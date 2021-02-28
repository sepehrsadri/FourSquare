package com.sadri.foursquare.di.data.dispatcher

import com.sadri.foursquare.ui.utils.DispatcherProvider
import com.sadri.foursquare.utils.idling_resource.EspressoTrackedDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatcherModule::class]
)
object TestDispatcherModule {
    private val dispatcher = object : DispatcherProvider {

        override fun ui(): CoroutineDispatcher = Dispatchers.Main

        override fun io(): CoroutineDispatcher = EspressoTrackedDispatcher(Dispatchers.IO)

        override fun default(): CoroutineDispatcher = EspressoTrackedDispatcher(Dispatchers.Default)

    }

    @Provides
    @Singleton
    fun providerDispatcher(): DispatcherProvider {
        return dispatcher
    }
}