package com.sadri.foursquare.utils.network

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Singleton
class ConnectionStateMonitor @Inject constructor(
    @ApplicationContext context: Context
) {
    private val _hasInternet = MutableLiveData<Boolean>()
    val hasInternet: LiveData<Boolean>
        get() = _hasInternet

    init {
        _hasInternet.value = context.isNetworkAvailable()
        ConnectionLiveData(context).observeForever {
            _hasInternet.value = it
        }
    }
}

private class ConnectionLiveData(val context: Context) : LiveData<Boolean>() {

    private var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val connectivityManagerCallback: ConnectivityManager.NetworkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network?) {
                postValue(true)
            }

            override fun onLost(network: Network?) {
                postValue(false)
            }
        }

    init {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ->
                registerNApiNetworkCallback()
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ->
                registerLollipopApiNetworkCallback()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun registerNApiNetworkCallback() =
        connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun registerLollipopApiNetworkCallback() {
        val builder = NetworkRequest.Builder()
            .addTransportType(android.net.NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(android.net.NetworkCapabilities.TRANSPORT_WIFI)
        connectivityManager.registerNetworkCallback(
            builder.build(),
            connectivityManagerCallback
        )
    }
}