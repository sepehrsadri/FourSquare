package com.sadri.foursquare.di.screen

import androidx.lifecycle.ViewModelProvider
import com.sadri.foursquare.di.screen.dashboard.DashboardViewModelsModule
import com.sadri.foursquare.di.utils.view_model.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Module(
    includes = [
        DashboardViewModelsModule::class
    ]
)
abstract class ViewModelsModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}