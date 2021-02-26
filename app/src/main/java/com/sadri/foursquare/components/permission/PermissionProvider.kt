package com.sadri.foursquare.components.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.sadri.foursquare.data.utils.KeyValueStorage
import com.sadri.foursquare.utils.gps.isGpsAvailable
import com.sadri.foursquare.utils.live_data.SingleLiveEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Suppress("SameParameterValue")
@Singleton
class PermissionProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val keyValueStorage: KeyValueStorage
) {

    companion object {
        private const val STORAGE_LOCATION_PERMISSION_REQUESTED_KEY: String =
            "PermissionManager.LOCATION_PERMISSION_REQUESTED_KEY"

        const val LOCATION_PERMISSIONS_REQUEST_ID: Int = 101
        const val LOCATION_PERMISSIONS_REQUEST_ID_RATIONAL: Int = 201
    }

    private val permissionResult = MutableLiveData<Int>()

    val locationPermissionResult = SingleLiveEvent<PermissionResult>()

    private val LOCATION_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val LOCATION_PERMISSION_REQUEST_ID = arrayOf(
        LOCATION_PERMISSIONS_REQUEST_ID,
        LOCATION_PERMISSIONS_REQUEST_ID_RATIONAL
    )

    init {
        permissionResult.observeForever {
            if (LOCATION_PERMISSION_REQUEST_ID.contains(it).not()) {
                return@observeForever
            }

            var result = PermissionResult.Accept

            if (hasLocationPermission().not()) {
                result =
                    if (it == LOCATION_PERMISSIONS_REQUEST_ID_RATIONAL)
                        PermissionResult.Ban else PermissionResult.Deny
            }

            locationPermissionResult.value = result
        }
    }

    // region Location
    fun hasLocationPermission(): Boolean {
        return hasPermissions(LOCATION_PERMISSIONS)
    }

    fun isGpsAvailable() = context.isGpsAvailable()

    fun requestLocationPermission(activity: Activity) {
        checkAndRequestPermission(
            activity,
            LOCATION_PERMISSIONS,
            STORAGE_LOCATION_PERMISSION_REQUESTED_KEY,
            LOCATION_PERMISSIONS_REQUEST_ID,
            LOCATION_PERMISSIONS_REQUEST_ID_RATIONAL
        )
    }
    // endregion

    fun onPermissionResult(code: Int): Boolean {
        permissionResult.value = code

        return code == LOCATION_PERMISSIONS_REQUEST_ID ||
                code == LOCATION_PERMISSIONS_REQUEST_ID_RATIONAL
    }

    private fun hasPermissions(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (!hasPermission(permission)) return false
        }
        return true
    }

    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isLocationAvailableAndAccessible(): Boolean {
        return isGpsAvailable() && hasLocationPermission()
    }

    private fun checkAndRequestPermission(
        activity: Activity,
        permissions: Array<String>,
        saveKey: String,
        normalRequestID: Int,
        rationalRequestID: Int
    ): Boolean {
        if (hasPermissions(permissions)) {
            return true
        }

        val isRational: Boolean = shouldShowRequestPermissionRationale(activity, permissions)

        val askedBefore = keyValueStorage.getBool(saveKey)

        requestPermission(
            activity,
            permissions,
            if (askedBefore && !isRational) rationalRequestID else normalRequestID
        )

        if (!askedBefore)
            keyValueStorage.putBool(saveKey, true)

        return false
    }

    private fun requestPermission(
        activity: Activity,
        permissions: Array<String>,
        requestCode: Int
    ) = ActivityCompat.requestPermissions(activity, permissions, requestCode)

    private fun shouldShowRequestPermissionRationale(
        activity: Activity,
        permissions: Array<String>
    ): Boolean {
        for (permission in permissions) {
            if (
                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    permission
                )
            ) return true
        }
        return false
    }
}