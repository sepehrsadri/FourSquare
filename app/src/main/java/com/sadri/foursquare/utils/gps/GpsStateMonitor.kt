package com.sadri.foursquare.utils.gps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sadri.foursquare.di.app.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Singleton
class GpsStateMonitor @Inject constructor(
    @ApplicationContext context: Context
) {
    private var lastGpsAvailability = context.isGpsAvailable()

    private val _hasGps = MutableLiveData<Boolean>()
    val hasGps: LiveData<Boolean>
        get() = _hasGps

    private val gpsBroadcastState = MutableLiveData<Boolean>()

    init {
        GpsBroadcastReceiver.build(
            context,
            gpsBroadcastState
        )
        gpsBroadcastState.observeForever {
            if (lastGpsAvailability != it) {
                _hasGps.value = it
                lastGpsAvailability = it
            }
        }
    }
}

private class GpsBroadcastReceiver(private val hasGps: MutableLiveData<Boolean>) :
    BroadcastReceiver() {
    companion object {
        fun build(
            context: Context,
            hasGps: MutableLiveData<Boolean>
        ) {
            context.registerReceiver(
                GpsBroadcastReceiver(hasGps),
                IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
            )
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val isGpsAvailable = context?.isGpsAvailable()

        if (isGpsAvailable == hasGps.value)
            return

        Timber.d("new GPS status, isGPSOn:  $isGpsAvailable")

        hasGps.value = isGpsAvailable
    }
}