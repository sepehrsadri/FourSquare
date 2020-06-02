package com.sadri.foursquare

import android.app.Application
import com.sadri.foursquare.components.logging.Logger
import com.sadri.foursquare.di.app.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
class App : Application(), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        Logger.init()

        AppInjector.init(this)
    }

    override fun androidInjector() = androidInjector
}