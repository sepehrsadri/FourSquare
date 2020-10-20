package com.sadri.foursquare.di.screen.dashboard

import androidx.lifecycle.ViewModel
import com.sadri.foursquare.di.utils.view_model.ViewModelKey
import com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.explore.DashboardMviViewModel
import com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.venue_detail.VenueDetailMviViewModel
import com.sadri.foursquare.ui.screens.requirement_satisfier.RequirementSatisfierMviViewModel
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
    @ViewModelKey(DashboardMviViewModel::class)
    abstract fun bindMainDashboardViewModel(viewModel: DashboardMviViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RequirementSatisfierMviViewModel::class)
    abstract fun bindRequirementSatisfierViewModel(
        viewModel: RequirementSatisfierMviViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VenueDetailMviViewModel::class)
    abstract fun bindVenueDetailViewModel(viewModel: VenueDetailMviViewModel): ViewModel
}