package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.explore

import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.sadri.foursquare.R
import com.sadri.foursquare.components.location.LocationProvider
import com.sadri.foursquare.components.permission.PermissionProvider
import com.sadri.foursquare.data.repositories.explore.EXPLORE_DATA_OFFSET
import com.sadri.foursquare.data.repositories.explore.EXPLORE_MAXIMUM_PAGE
import com.sadri.foursquare.data.repositories.explore.ExploreDataSingleSourceOfTruth
import com.sadri.foursquare.data.repositories.explore.ExploreResult
import com.sadri.foursquare.data.repositories.venue_detail.VenueDetailSingleSourceOfTruth
import com.sadri.foursquare.data.utils.Result
import com.sadri.foursquare.models.venue.Venue
import com.sadri.foursquare.ui.utils.Coordinator
import com.sadri.foursquare.ui.utils.mvi.BaseMviViewModel
import com.sadri.foursquare.ui.utils.mvi.BaseState
import com.sadri.foursquare.utils.gps.GpsStateMonitor
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardMviViewModel @Inject constructor(
    gpsStateMonitor: GpsStateMonitor,
    private val permissionProvider: PermissionProvider,
    private val exploreDataSingleSourceOfTruth: ExploreDataSingleSourceOfTruth,
    private val venueDetailSingleSourceOfTruth: VenueDetailSingleSourceOfTruth,
    private val coordinator: Coordinator,
    locationProvider: LocationProvider
) : BaseMviViewModel<DashboardViewState, DashboardIntent, DashboardResult>(DashboardViewState.idle()),
    DashboardListAdapterContract {
    override fun reduce(
        previousState: DashboardViewState,
        result: DashboardResult
    ): DashboardViewState =
        when (result) {
            DashboardResult.Loading -> {
                previousState.copy(
                    base = BaseState.loading(),
                    venueListChanged = false
                )
            }
            is DashboardResult.VenueListFetched -> {
                previousState.copy(
                    base = BaseState.stable(),
                    venuesList = result.venues,
                    venueListChanged = true,
                    venuesListAvailability = true
                )
            }
            is DashboardResult.Error -> {
                previousState.copy(
                    base = BaseState.showSnackbar(result.message),
                    venueListChanged = false,
                    venuesListAvailability = venuesList.isNotEmpty()
                )
            }
            is DashboardResult.ResMsg -> {
                previousState.copy(
                    base = BaseState.showSnackbar(result.message),
                    venueListChanged = false,
                    venuesListAvailability = venuesList.isNotEmpty()
                )
            }
        }

    override fun dispatch(intent: DashboardIntent) {
        super.dispatch(intent)

        when (intent) {
            DashboardIntent.Scroll -> {
                onScroll()
            }
            DashboardIntent.RefreshExplores -> {
                refreshExplores()
            }
            DashboardIntent.Init -> {
                initFetch()
            }
        }

    }

    private var page: Int = 0

    private val venuesList: MutableList<Venue> = ArrayList()

    private var isLoading = false

    private val exploreObserver =
        Observer<Result<ExploreResult>> {
            when (it) {
                is Result.Loading -> {
                    newResult(DashboardResult.Loading)
                }
                is Result.Success -> {
                    newResult(DashboardResult.VenueListFetched(updateVenuesList(it)))
                    updateVenuesList(it)
                    isLoading = false
                }
                is Result.Error -> {
                    newResult(DashboardResult.Error(it.error.message))
                }
                Result.Empty -> {
                    newResult(DashboardResult.ResMsg(R.string.error_unknown))
                }
            }
        }

    private fun updateVenuesList(it: Result.Success<ExploreResult>): List<Venue> {
        val data = it.data
        val venues = data.result
        val offset = data.offset
        val reliable = data.reliable

        if (reliable && venuesList.size >= offset + EXPLORE_DATA_OFFSET) {

            for ((index, i) in
            (offset until offset + EXPLORE_DATA_OFFSET).withIndex()) {
                venuesList[i] = venues[index]
            }
        } else {
            venuesList.addAll(venues)
        }

        return ArrayList(venuesList.toList())
    }

    init {
        observeWithInitUpdate(
            gpsStateMonitor.hasGps,
            Observer {
                if (permissionProvider.isLocationAvailableAndAccessible().not()) {
                    coordinator.navigate(DashboardMviFragmentDirections.navigateToRequirementSatisfierFragment())
                }
            }
        )
        observe(
            locationProvider.locationChange,
            Observer {
                newResult(DashboardResult.ResMsg(R.string.location_updating))
                venuesList.clear()
                page = 0
                fetchVenues()
                invalidateVenueDetailSource()
            }
        )
    }

    private fun invalidateVenueDetailSource() {
        viewModelScope.launch {
            venueDetailSingleSourceOfTruth
                .venueDetailPersistentDataSource
                .clear()
        }
    }


    private fun onScroll() {
        page++
        fetchVenues()
    }

    private fun fetchVenues() {
        if (isLoading || isReachMaximumPage()) {
            return
        }
        isLoading = true

        observe(
            exploreDataSingleSourceOfTruth.fetchExplores(
                page * EXPLORE_DATA_OFFSET
            ),
            exploreObserver
        )
    }

    private fun isReachMaximumPage() = page > EXPLORE_MAXIMUM_PAGE

    private fun initFetch() {
        if (venuesList.isEmpty()) {
            fetchVenues()
        }
    }

    private fun refreshExplores() {
        viewModelScope.launch {
            exploreDataSingleSourceOfTruth.explorePersistentDataSource.clear()
            venuesList.clear()
            page = 0
            fetchVenues()
        }
    }

    override fun onVenueClick(venue: Venue) {
        coordinator.navigate(
            DashboardMviFragmentDirections
                .navigateToVenueDetailFragment(
                    venueId = venue.id
                )
        )
    }
}