package com.sadri.foursquare.di.data.scheduler

import com.sadri.foursquare.data.scheduler.AppSchedulerProvider
import com.sadri.foursquare.data.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Module
object SchedulerModule {
    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}