package com.sadri.foursquare.ui.screens.splash

import android.content.Intent
import android.os.Bundle
import com.sadri.foursquare.ui.screens.dashboard.DashboardActivity
import com.sadri.foursquare.ui.utils.BaseActivity

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))

        finish()
    }
}