package com.sadri.foursquare.components.logging

import com.sadri.foursquare.BuildConfig
import timber.log.Timber

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
object Logger {
    fun init() {
        Timber.plant(
            if (BuildConfig.DEBUG) {
                Timber.DebugTree()
            } else {
                ProductionLogTree()
            }
        )
    }
}