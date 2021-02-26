package com.sadri.foursquare.utils.gps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
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
    private val _hasGps = MutableLiveData(context.isGpsAvailable())
    val hasGps: LiveData<Boolean>
        get() = _hasGps

    init {
        GpsBroadcastReceiver.build(
            context,
            _hasGps
        )
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