package com.sadri.foursquare.di.data.persistent

import android.content.Context
import com.sadri.foursquare.data.persistent.AppDatabase
import com.sadri.foursquare.data.persistent.venue.ExploreDao
import com.sadri.foursquare.di.app.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Module
object PersistentModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getAppDataBase(context)
    }

    @Provides
    @Singleton
    fun provideVenueDao(appDatabase: AppDatabase): ExploreDao {
        return appDatabase.venueDao()
    }
}