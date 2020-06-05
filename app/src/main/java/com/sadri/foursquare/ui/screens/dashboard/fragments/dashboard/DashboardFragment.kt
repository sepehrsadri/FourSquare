package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sadri.foursquare.R
import com.sadri.foursquare.components.permission.PermissionProvider
import com.sadri.foursquare.ui.navigation.NavigationFragment
import com.sadri.foursquare.ui.navigation.NavigationViewModel
import com.sadri.foursquare.ui.utils.EndlessRecyclerOnScrollListener
import com.sadri.foursquare.ui.utils.toast
import kotlinx.android.synthetic.main.fragment_dashboard.*
import javax.inject.Inject

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
class DashboardFragment : NavigationFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var permissionProvider: PermissionProvider

    private val viewModel: DashboardViewModel by viewModels { viewModelFactory }
    override fun getViewModel(): NavigationViewModel = viewModel

    private val recyclerViewScrollViewListener =
        object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                viewModel.onScroll()
            }
        }

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
            viewModel.refreshExplores()
            swipeRefreshLayout.isRefreshing = false
        }

        viewModel.toast.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    requireContext().toast(it)
                }
            }
        )

        val adapter = DashboardListAdapter(
            viewModel
        )

        viewModel.venues.observe(
            viewLifecycleOwner,
            Observer {
                adapter.submitList(it)
            }
        )

        with(rcvVenues) {
            this.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            this.adapter = adapter
        }
    }

    override fun onStart() {
        super.onStart()
        rcvVenues.addOnScrollListener(recyclerViewScrollViewListener)
        if (permissionProvider.isLocationAvailableAndAccessible()) {
            viewModel.initFetch()
        }
    }

    override fun onStop() {
        rcvVenues.removeOnScrollListener(recyclerViewScrollViewListener)
        super.onStop()
    }
}