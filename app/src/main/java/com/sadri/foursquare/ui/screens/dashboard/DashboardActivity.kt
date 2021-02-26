package com.sadri.foursquare.ui.screens.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sadri.foursquare.R
import com.sadri.foursquare.components.permission.PermissionProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    @Inject
    lateinit var permissionProvider: PermissionProvider


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (permissionProvider.onPermissionResult(requestCode).not())
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}