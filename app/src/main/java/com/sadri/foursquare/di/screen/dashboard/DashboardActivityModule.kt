package com.sadri.foursquare.di.screen.dashboard

import com.sadri.foursquare.di.screen.PerFragment
import com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.DashboardFragment
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
abstract class DashboardActivityModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun contributeMainDashboardFragment(): DashboardFragment
}