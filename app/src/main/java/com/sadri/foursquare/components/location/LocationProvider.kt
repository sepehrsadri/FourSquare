package com.sadri.foursquare.components.location

import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.sadri.foursquare.components.permission.PermissionProvider
import com.sadri.foursquare.di.app.ApplicationContext
import com.sadri.foursquare.models.MyPoint
import com.sadri.foursquare.utils.mappers.toMyPoint
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Singleton
class LocationProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val permissionProvider: PermissionProvider
) {
    private val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    fun getLastLocation(): MyPoint {
        if (permissionProvider.isLocationAvailableAndAccessible().not()) {
            return DEFAULT_POINT
        }
        var locationGPS: Location? = null
        var locationNet: Location? = null

        try {
            locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        } catch (securityException: SecurityException) {
            Timber.e("Location Permission is not permitted ! ")
        }
        var gpsLocationTime: Long = 0
        if (locationGPS != null) {
            gpsLocationTime = locationGPS.time
        }
        var netLocationTime: Long = 0
        if (locationNet != null) {
            netLocationTime = locationNet.time
        }
        val resultLocation = if (0 < gpsLocationTime - netLocationTime) {
            locationGPS
        } else {
            locationNet
        }
        return resultLocation?.toMyPoint() ?: DEFAULT_POINT
    }

    companion object {
        // Tehran Point
        private val DEFAULT_POINT = MyPoint(
            35.727954,
            51.389634
        )
    }

}