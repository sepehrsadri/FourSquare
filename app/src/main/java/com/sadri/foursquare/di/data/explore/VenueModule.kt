package com.sadri.foursquare.di.data.explore

import com.sadri.foursquare.data.api.RetrofitProvider
import com.sadri.foursquare.data.repositories.explore.ExploreServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Module
object VenueModule {
    @Provides
    @Singleton
    fun provideExploreApiServices(retrofit: Retrofit): ExploreServices {
        return RetrofitProvider.provideService(
            retrofit,
            ExploreServices::class.java
        )
    }
}