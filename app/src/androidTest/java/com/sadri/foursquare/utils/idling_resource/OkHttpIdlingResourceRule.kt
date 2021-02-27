package com.sadri.foursquare.utils.idling_resource

import androidx.test.espresso.IdlingRegistry
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.sadri.foursquare.data.api.RetrofitProvider
import org.junit.rules.TestWatcher
import org.junit.runner.Description


class OkHttpIdlingResourceRule : TestWatcher() {
    private val idlingResource = OkHttp3IdlingResource.create("OkHttp", RetrofitProvider.CLIENT)

    override fun finished(description: Description?) {
        IdlingRegistry.getInstance().unregister(idlingResource)
        super.finished(description)
    }

    override fun starting(description: Description?) {
        IdlingRegistry.getInstance().register(idlingResource)
        super.starting(description)
    }
}
