package com.sadri.foursquare.di.data.dispatcher

import com.sadri.foursquare.ui.utils.DefaultDispatcher
import com.sadri.foursquare.ui.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    fun providerDispatcher(): DispatcherProvider {
        return DefaultDispatcher()
    }
}