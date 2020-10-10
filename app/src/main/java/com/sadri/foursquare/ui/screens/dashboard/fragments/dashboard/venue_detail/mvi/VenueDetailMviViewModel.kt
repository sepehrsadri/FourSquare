package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.venue_detail.mvi

import androidx.lifecycle.Observer
import com.sadri.foursquare.data.repositories.venue_detail.VenueDetailSingleSourceOfTruth
import com.sadri.foursquare.data.utils.Result
import com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.venue_detail.convertToUiDataModel
import com.sadri.foursquare.ui.utils.mvi.BaseMviViewModel
import com.sadri.foursquare.ui.utils.mvi.BaseState
import javax.inject.Inject

class VenueDetailMviViewModel @Inject constructor(
    private val venueDetailSingleSourceOfTruth: VenueDetailSingleSourceOfTruth
) : BaseMviViewModel<VenueDetailViewState, VenueDetailIntent, VenueDetailResult>(
    VenueDetailViewState.idle()
) {
    override fun reduce(
        previousState: VenueDetailViewState,
        result: VenueDetailResult
    ): VenueDetailViewState =
        when (result) {
            is VenueDetailResult.Error -> {
                previousState.copy(
                    base = BaseState.showError(result.message)
                )
            }
            is VenueDetailResult.Success -> previousState.copy(
                base = BaseState.stable(),
                result = convertToUiDataModel(result.result)
            )
            VenueDetailResult.Loading -> previousState.copy(
                base = BaseState.loading()
            )
        }

    override fun dispatch(intent: VenueDetailIntent) {
        super.dispatch(intent)
        when (intent) {
            is VenueDetailIntent.Fetch -> {
                fetch(intent.id)
            }
        }
    }

    private fun fetch(id: String) {
        if (viewStates().value!!.base.showLoading) return

        observe(
            venueDetailSingleSourceOfTruth.limitedFetchVenueDetail(id),
            Observer {
                when (it) {
                    is Result.Loading -> {
                        newResult(VenueDetailResult.Loading)
                    }
                    is Result.Success -> {
                        newResult(VenueDetailResult.Success(it.data))
                    }
                    is Result.Error -> {
                        newResult(VenueDetailResult.Error(it.error.message))
                    }
                    Result.Empty -> {
                        // ignore
                    }
                }
            }
        )
    }

}