package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard

import androidx.lifecycle.Observer
import com.sadri.foursquare.components.permission.PermissionProvider
import com.sadri.foursquare.ui.navigation.NavigationViewModel
import com.sadri.foursquare.utils.gps.GpsStateMonitor
import javax.inject.Inject

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
class DashboardViewModel @Inject constructor(
    gpsStateMonitor: GpsStateMonitor,
    private val permissionProvider: PermissionProvider
) : NavigationViewModel() {

    init {
        observeWithInitUpdate(
            gpsStateMonitor.hasGps,
            Observer {
                if (permissionProvider.isLocationAvailableAndAccessible().not()) {
                    navigate(DashboardFragmentDirections.navigateToRequirementSatisfierFragment())
                }
            }
        )
    }
}