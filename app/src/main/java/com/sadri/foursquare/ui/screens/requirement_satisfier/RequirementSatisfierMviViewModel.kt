package com.sadri.foursquare.ui.screens.requirement_satisfier

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
import com.sadri.foursquare.ui.utils.Coordinator
import com.sadri.foursquare.ui.utils.mvi.BaseMviViewModelContainsOwner
import com.sadri.foursquare.ui.utils.mvi.BaseState
import com.sadri.foursquare.utils.gps.GpsStateMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@HiltViewModel
class RequirementSatisfierMviViewModel @Inject constructor(
    private val permissionProvider: PermissionProvider,
    private val coordinator: Coordinator,
    gpsStateMonitor: GpsStateMonitor
) : BaseMviViewModelContainsOwner<RequirementSatisfierViewModelOwner, RequirementSatisfierViewState, RequirementSatisfierIntent, RequirementSatisfierResult>(
    RequirementSatisfierViewState()
) {

    private var isPermissionDenied = false

    override fun reduce(
        previousState: RequirementSatisfierViewState,
        result: RequirementSatisfierResult
    ): RequirementSatisfierViewState =
        when (result) {
            RequirementSatisfierResult.RequestGps -> {
                previousState.copy(
                    base = BaseState.stable(),
                    requestPermission = RequestPermission.GPS
                )
            }
            is RequirementSatisfierResult.RequestLocation -> {
                previousState.copy(
                    base = BaseState.stable(),
                    requestPermission = RequestPermission.Location(result.exception)
                )
            }
            is RequirementSatisfierResult.Toast -> {
                previousState.copy(
                    base = BaseState.showToast(result.message),
                    requestPermission = RequestPermission.Nothing
                )
            }
            is RequirementSatisfierResult.Result -> {
                previousState.copy(
                    base = BaseState.stable(),
                    dataModel = result.res,
                    requestPermission = RequestPermission.Nothing
                )
            }
        }

    override fun dispatch(intent: RequirementSatisfierIntent) {
        super.dispatch(intent)
        when (intent) {
            RequirementSatisfierIntent.RequestPermissionAndLocationSwitch -> {
                requestPermissionAndLocationSwitch()
            }
        }
    }


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

        newResult(RequirementSatisfierResult.Result(dataModel))
    }

    private fun requestPermissionAndLocationSwitch() {
        if (checkAvailability()) {
            return
        }

        if (isPermissionDenied) {
            showPermissionDeniedToast()
            return
        }

        if (permissionProvider.hasLocationPermission().not()) {
            newResult(RequirementSatisfierResult.RequestGps)
            return
        }

        enableHighAccuracySettingClient()
    }

    private fun showPermissionDeniedToast() {
        showToast(R.string.toast_permission)
    }

    private fun checkAvailability(): Boolean {
        if (permissionProvider.isLocationAvailableAndAccessible()) {
            coordinator.navigate(NavigationCommand.BackTo(R.id.dashboardFragment))
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
                        newResult(RequirementSatisfierResult.RequestLocation(apiException as ResolvableApiException))
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        showToast(R.string.toast_gps)
                    }
                }
            }
        }
    }

    private fun showToast(textId: Int) {
        newResult(RequirementSatisfierResult.Toast(textId))
    }

}