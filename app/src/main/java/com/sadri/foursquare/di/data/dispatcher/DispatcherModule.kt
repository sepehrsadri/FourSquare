package com.sadri.foursquare.di.data.dispatcher

import com.sadri.foursquare.ui.utils.DefaultDispatcher
import com.sadri.foursquare.ui.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    @Singleton
    fun providerDispatcher(): DispatcherProvider {
        return DefaultDispatcher()
    }
}