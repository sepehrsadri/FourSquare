package com.sadri.foursquare.di.screen.dashboard

import androidx.lifecycle.ViewModel
import com.sadri.foursquare.di.utils.view_model.ViewModelKey
import com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.explore.DashboardViewModel
import com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.venue_detail.VenueDetailViewModel
import com.sadri.foursquare.ui.screens.requirement_satisfier.RequirementSatisfierViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Suppress("unused")
@Module
abstract class DashboardViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindMainDashboardViewModel(viewModel: DashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RequirementSatisfierViewModel::class)
    abstract fun bindRequirementSatisfierViewModel(
        viewModel: RequirementSatisfierViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VenueDetailViewModel::class)
    abstract fun bindVenueDetailViewModel(viewModel: VenueDetailViewModel): ViewModel
}