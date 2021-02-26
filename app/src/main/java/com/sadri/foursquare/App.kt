package com.sadri.foursquare

import android.app.Application
import com.sadri.foursquare.components.logging.Logger
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Logger.init()
    }
}