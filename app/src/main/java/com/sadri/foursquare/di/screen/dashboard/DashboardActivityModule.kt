package com.sadri.foursquare.di.screen.dashboard

import com.sadri.foursquare.di.screen.PerFragment
import com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.explore.DashboardMviFragment
import com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.venue_detail.VenueDetailMviFragment
import com.sadri.foursquare.ui.screens.requirement_satisfier.RequirementSatisfierMviFragment
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
    abstract fun contributeMainDashboardFragment(): DashboardMviFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun contributeRequirementSatisfierFragment(): RequirementSatisfierMviFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun contributeVenueDetailFragment(): VenueDetailMviFragment
}