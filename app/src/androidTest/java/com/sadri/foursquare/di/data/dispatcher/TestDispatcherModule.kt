package com.sadri.foursquare.di.data.dispatcher

import android.os.AsyncTask
import com.sadri.foursquare.ui.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatcherModule::class]
)
object TestDispatcherModule {
    private val dispatcher = object : DispatcherProvider {

        override fun ui(): CoroutineDispatcher = Dispatchers.Main

        override fun io(): CoroutineDispatcher =
            AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()

        override fun default(): CoroutineDispatcher =
            AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()

    }

    @Provides
    @Singleton
    fun providerDispatcher(): DispatcherProvider {
        return dispatcher
    }
}