package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sadri.foursquare.R
import com.sadri.foursquare.components.permission.PermissionProvider
import com.sadri.foursquare.ui.utils.EndlessRecyclerOnScrollListener
import com.sadri.foursquare.ui.utils.mvi.BaseMviFragment
import com.sadri.foursquare.utils.network.ConnectionStateMonitor
import kotlinx.android.synthetic.main.fragment_dashboard.*
import javax.inject.Inject

class DashboardMviFragment :
    BaseMviFragment<DashboardViewState, DashboardIntent, DashboardMviViewModel>() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var permissionProvider: PermissionProvider

    @Inject
    lateinit var connectionStateMonitor: ConnectionStateMonitor

    override val viewModel: DashboardMviViewModel by viewModels { viewModelFactory }

    private lateinit var adapter: DashboardListAdapter

    private val recyclerViewScrollViewListener =
        object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                viewModel.dispatch(DashboardIntent.Scroll)
            }
        }

    override fun container(): View = container

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.dispatch(DashboardIntent.RefreshExplores)
            swipeRefreshLayout.isRefreshing = false
        }

        connectionStateMonitor.hasInternet.observe(
            viewLifecycleOwner,
            Observer {
                val visibility = if (it) View.GONE else View.VISIBLE
                noInternetRoot.visibility = visibility
            }
        )

        crossIv.setOnClickListener {
            noInternetRoot.visibility = View.GONE
        }

        this.adapter =
            DashboardListAdapter(
                viewModel
            )

        rcvVenues.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        rcvVenues.adapter = this.adapter
    }

    override fun render(viewState: DashboardViewState) {
        super.render(viewState)

        with(viewState) {
            if (venuesListAvailability) {
                makeListViewVisible()
            } else {
                makeListViewGone()
            }

            if (venueListChanged) {
                adapter.submitList(venuesList)
            }
        }
    }

    private fun makeListViewVisible() {
        if (rcvVenues.isVisible.not() || noLocationView.isVisible) {
            noLocationView.visibility = View.GONE
            rcvVenues.visibility = View.VISIBLE
        }
    }

    private fun makeListViewGone() {
        if (noLocationView.isVisible.not() || rcvVenues.isVisible) {
            noLocationView.visibility = View.VISIBLE
            rcvVenues.visibility = View.GONE
        }
    }

    override fun onStart() {
        super.onStart()
        rcvVenues.addOnScrollListener(recyclerViewScrollViewListener)
        if (permissionProvider.isLocationAvailableAndAccessible()) {
            viewModel.dispatch(DashboardIntent.Init)
        }
    }

    override fun onStop() {
        rcvVenues.removeOnScrollListener(recyclerViewScrollViewListener)
        super.onStop()
    }
}