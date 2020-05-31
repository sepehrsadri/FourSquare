package com.sadri.foursquare.di.screen

import com.sadri.foursquare.di.screen.dashboard.DashboardActivityModule
import com.sadri.foursquare.ui.screens.dashboard.DashboardActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(
        modules = [
            DashboardActivityModule::class
        ]
    )
    @PerActivity
    abstract fun contributeDashboardActivity(): DashboardActivity
}