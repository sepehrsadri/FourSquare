package com.sadri.foursquare.ui.screens.requirement_satisfier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.tasks.Task
import com.sadri.foursquare.R
import com.sadri.foursquare.components.permission.PermissionProvider
import com.sadri.foursquare.components.permission.PermissionResult
import com.sadri.foursquare.ui.navigation.NavigationCommand
import com.sadri.foursquare.ui.utils.owner_view_model.OwnerViewModel
import com.sadri.foursquare.utils.gps.GpsStateMonitor
import com.sadri.foursquare.utils.live_data.SingleLiveEvent
import javax.inject.Inject

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
class RequirementSatisfierViewModel @Inject constructor(
    private val permissionProvider: PermissionProvider,
    gpsStateMonitor: GpsStateMonitor
) : OwnerViewModel<RequirementSatisfierViewModelOwner>() {

    val requestPermissionSignal = SingleLiveEvent<Nothing>()
    val toast = SingleLiveEvent<Int>()
    val requestLocationResolutionSignal = SingleLiveEvent<ResolvableApiException>()

    private val _dataModel = MutableLiveData<RequirementSatisfierDataModel>()
    val dataModel: LiveData<RequirementSatisfierDataModel>
        get() = _dataModel

    private var isPermissionDenied = false

    init {
        observeWithInitUpdate(
            gpsStateMonitor.hasGps,
            Observer {
                checkAvailability()
            }
        )
        observe(
            permissionProvider.locationPermissionResult,
            Observer {
                when (it) {
                    PermissionResult.Accept -> {
                        checkAvailability()
                        updateDataModel()
                    }
                    PermissionResult.Deny -> {
                        // ignore
                    }
                    PermissionResult.Ban -> {
                        showPermissionDeniedToast()
                        isPermissionDenied = true
                    }
                }
            }
        )
        updateDataModel()
    }

    private fun updateDataModel() {
        val dataModel = when {
            permissionProvider.hasLocationPermission().not() ->
                RequirementSatisfierDataModel.Permission

            permissionProvider.isGpsAvailable().not() ->
                RequirementSatisfierDataModel.Location

            else -> RequirementSatisfierDataModel.Permission
        }

        _dataModel.value = dataModel
    }

    fun requestPermissionAndLocationSwitch() {
        if (checkAvailability()) {
            return
        }

        if (isPermissionDenied) {
            showPermissionDeniedToast()
            return
        }

        if (permissionProvider.hasLocationPermission().not()) {
            requestPermissionSignal.call()
            return
        }

        enableHighAccuracySettingClient()
    }

    private fun showPermissionDeniedToast() {
        showToast(R.string.toast_permission)
    }

    private fun checkAvailability(): Boolean {
        if (permissionProvider.isLocationAvailableAndAccessible()) {
            navigate(NavigationCommand.BackTo(R.id.dashboardFragment))
            return true
        }
        return false
    }

    private fun enableHighAccuracySettingClient() {
        if (hasOwner().not()) {
            return
        }

        val result: Task<LocationSettingsResponse> = getOwner()!!.getLocationSettingTask()

        result.addOnSuccessListener {
            checkAvailability()
        }

        result.addOnCompleteListener {

            try {
                it.getResult(ApiException::class.java)
            } catch (apiException: ApiException) {

                when (apiException.statusCode) {

                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        requestLocationResolutionSignal.value =
                            apiException as ResolvableApiException
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        showToast(R.string.toast_gps)
                    }
                }
            }
        }
    }

    private fun showToast(textId: Int) {
        toast.value = textId
    }
}