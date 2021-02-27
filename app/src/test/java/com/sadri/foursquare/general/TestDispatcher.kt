package com.sadri.foursquare.general

import com.sadri.foursquare.ui.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

internal object TestDispatcher : DispatcherProvider {
    @ExperimentalCoroutinesApi
    private val testDispatcher by lazy {
        TestCoroutineDispatcher()
    }

    override fun ui(): CoroutineDispatcher = testDispatcher
    override fun io(): CoroutineDispatcher = testDispatcher
    override fun default(): CoroutineDispatcher = testDispatcher
}