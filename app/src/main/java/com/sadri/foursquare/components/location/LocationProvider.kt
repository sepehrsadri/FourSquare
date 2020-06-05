package com.sadri.foursquare.components.location

import android.location.Location
import android.os.Handler
import android.os.Looper
import com.sadri.foursquare.data.utils.KeyValueStorage
import com.sadri.foursquare.data.utils.LocationResult
import com.sadri.foursquare.models.MyPoint
import com.sadri.foursquare.utils.isSame
import com.sadri.foursquare.utils.live_data.SingleLiveEvent
import com.sadri.foursquare.utils.toLocation
import com.sadri.foursquare.utils.toMyPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Sepehr Sadri on 6/5/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Singleton
class LocationProvider @Inject constructor(
    private val locationListener: LocationListener,
    private val keyValueStorage: KeyValueStorage
) {
    private val handler = Handler(Looper.getMainLooper())

    val locationChange = SingleLiveEvent<Nothing>()

    private val locationChangeRunnable =
        Runnable {
            locationChange.call()
        }

    init {
        locationListener.location.observeForever {
            handler.removeCallbacks(locationChangeRunnable)
            handler.postDelayed(locationChangeRunnable, LOCATION_UPDATE_DELAY)
        }
    }

    suspend fun getLastLocationResult(): LocationResult {
        val savedPoint = keyValueStorage.getMyPoint(LOCATION_STORAGE_KEY)

        val lastLocation = locationListener.getCurrentLocation()

        if (savedPoint == null) {
            saveNewLocation(lastLocation)
            return LocationResult(
                LocationResult.Status.EMPTY,
                lastLocation?.toMyPoint() ?: DEFAULT_POINT
            )
        }

        val savedLocation = savedPoint.toLocation()

        return if (
            lastLocation != null &&
            savedLocation.isSame(lastLocation).not() &&
            savedLocation.distanceTo(lastLocation) >=
            LocationListener.MINIMUM_LOCATION_METER_INTERVAL
        ) {
            saveNewLocation(lastLocation)
            LocationResult(LocationResult.Status.CHANGED, lastLocation.toMyPoint())
        } else {
            LocationResult(LocationResult.Status.SAME, savedPoint)
        }
    }

    private fun saveNewLocation(location: Location?) =
        GlobalScope.launch(Dispatchers.Main) {
            if (location == null) {
                return@launch
            }
            keyValueStorage.putMyPoint(LOCATION_STORAGE_KEY, location.toMyPoint())
        }

    companion object {
        private const val LOCATION_UPDATE_DELAY = 3000L

        // Tehran Point
        private val DEFAULT_POINT = MyPoint(
            35.727954,
            51.389634
        )
        private const val LOCATION_STORAGE_KEY = "location_key_storage"
    }
}