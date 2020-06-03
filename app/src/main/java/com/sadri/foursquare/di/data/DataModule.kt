package com.sadri.foursquare.di.data

import android.content.Context
import com.sadri.foursquare.data.utils.KeyValueStorage
import com.sadri.foursquare.di.app.ApplicationContext
import com.sadri.foursquare.di.data.api.RetrofitModule
import com.sadri.foursquare.di.data.explore.ExploreModule
import com.sadri.foursquare.di.data.persistent.PersistentModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Module(
    includes = [
        RetrofitModule::class,
        ExploreModule::class,
        PersistentModule::class
    ]
)
object DataModule {

    @Provides
    @Singleton
    fun provideKeyValueStorage(
        @ApplicationContext context: Context
    ): KeyValueStorage {
        return KeyValueStorage(
            KeyValueStorage.getPrivateSharedPreferences(
                context
            )
        )
    }
}