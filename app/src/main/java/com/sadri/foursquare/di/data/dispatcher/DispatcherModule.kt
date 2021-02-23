package com.sadri.foursquare.di.data.dispatcher

import com.sadri.foursquare.ui.utils.DefaultDispatcher
import com.sadri.foursquare.ui.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DispatcherModule {
    @Singleton
    @Provides
    fun providerDispatcher(): DispatcherProvider {
        return DefaultDispatcher()
    }
}