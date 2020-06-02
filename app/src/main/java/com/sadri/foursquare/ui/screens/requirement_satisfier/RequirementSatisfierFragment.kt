package com.sadri.foursquare.ui.screens.requirement_satisfier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.Task
import com.sadri.foursquare.R
import com.sadri.foursquare.components.permission.PermissionProvider
import com.sadri.foursquare.ui.navigation.NavigationFragment
import com.sadri.foursquare.ui.navigation.NavigationViewModel
import com.sadri.foursquare.ui.utils.setSrcCompat
import com.sadri.foursquare.ui.utils.toast
import kotlinx.android.synthetic.main.fragment_requirement_satisfier.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
class RequirementSatisfierFragment : NavigationFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var permissionProvider: PermissionProvider

    private val viewModel: RequirementSatisfierViewModel by viewModels { viewModelFactory }
    override fun getViewModel(): NavigationViewModel = viewModel

    private val viewModelOwner =
        object : RequirementSatisfierViewModelOwner {
            override fun getLocationSettingTask(): Task<LocationSettingsResponse> {
                return LocationServices
                    .getSettingsClient(requireActivity())
                    .checkLocationSettings(locationSettingRequest)
            }
        }

    private val locationSettingRequest by lazy {
        LocationSettingsRequest.Builder()
            .addLocationRequest(getLocationRequest())
            .setAlwaysShow(true)
            .build()
    }

    private fun getLocationRequest(): LocationRequest = LocationRequest.create().apply {
        this.interval = 0
        this.fastestInterval = 0
        this.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_requirement_satisfier, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart(viewModelOwner)
    }

    override fun onStop() {
        viewModel.onStop()
        super.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewsListeners()

        setupObservers()
    }

    private fun setupViewsListeners() {
        actionBtn.setOnClickListener {
            viewModel.requestPermissionAndLocationSwitch()
        }
    }

    private fun setupObservers() {
        viewModel.dataModel.observe(
            viewLifecycleOwner,
            Observer {
                updateUI(it)
            }
        )

        viewModel.requestPermissionSignal.observe(
            viewLifecycleOwner,
            Observer {
                permissionProvider.requestLocationPermission(requireActivity())
            }
        )

        viewModel.requestLocationResolutionSignal.observe(
            viewLifecycleOwner,
            Observer {
                try {
                    it?.startResolutionForResult(
                        activity,
                        RESOLUTION_RESULT_REQUEST_CODE
                    )
                } catch (e: Exception) {
                    Timber.e(e, "problem accrued with request location")
                }
            }
        )

        viewModel.toast.observe(
            viewLifecycleOwner,
            Observer {
                requireContext().toast(it!!)
            }
        )
    }

    private fun updateUI(dataModel: RequirementSatisfierDataModel) {
        with(dataModel) {
            iconIv.setSrcCompat(icon)
            bioTv.setText(bio)
            bodyTv.setText(body)
            actionBtn.setText(action)
        }
    }

    companion object {
        private const val RESOLUTION_RESULT_REQUEST_CODE: Int = 1000
    }
}