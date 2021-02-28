package com.sadri.foursquare.utils.idling_resource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

class EspressoTrackedDispatcher(private val wrappedCoroutineDispatcher: CoroutineDispatcher) :
    CoroutineDispatcher() {

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        EspressoIdlingResource.increment()
        val blockWithDecrement = Runnable {
            try {
                block.run()
            } finally {
                runBlocking {
                    delay(500)
                    EspressoIdlingResource.decrement()
                }
            }
        }
        wrappedCoroutineDispatcher.dispatch(context, blockWithDecrement)
    }
}