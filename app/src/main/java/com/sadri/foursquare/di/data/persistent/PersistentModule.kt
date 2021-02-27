package com.sadri.foursquare.di.data.persistent

import android.content.Context
import com.sadri.foursquare.data.persistent.AppDatabase
import com.sadri.foursquare.data.persistent.venue.ExploreDao
import com.sadri.foursquare.data.persistent.venue_detail.VenueDetailDao
import com.sadri.foursquare.data.utils.KeyValueStorage
import com.sadri.foursquare.ui.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Module
@InstallIn(SingletonComponent::class)
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

    @Provides
    @Singleton
    fun provideVenueDetailDao(appDatabase: AppDatabase): VenueDetailDao {
        return appDatabase.venueDetailDao()
    }

    @Provides
    fun provideKeyValueStorage(
        @ApplicationContext context: Context,
        dispatcherProvider: DispatcherProvider
    ): KeyValueStorage {
        return KeyValueStorage(
            KeyValueStorage.getPrivateSharedPreferences(
                context
            ),
            dispatcherProvider
        )
    }
}