package com.sadri.foursquare.di.app

import android.content.Context
import com.sadri.foursquare.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Suppress("unused")
@Module
object AppModule {
    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(app: App): Context {
        return app.applicationContext
    }
}