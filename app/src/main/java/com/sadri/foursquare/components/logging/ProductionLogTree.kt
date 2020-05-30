package com.sadri.foursquare.components.logging

import android.util.Log
import timber.log.Timber

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
class ProductionLogTree : Timber.Tree() {
    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority > Log.DEBUG
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        // Crashlytics.log(priority, tag, message)
    }
}