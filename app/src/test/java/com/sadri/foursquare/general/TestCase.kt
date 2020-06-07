package com.sadri.foursquare.general

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
internal abstract class TestCase {
    /**
     * this rule provide main looper for testing live data
     */
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val uiThreadRule = MainCoroutineRule()

    init {
        MockitoAnnotations.initMocks(this)
    }

    @After
    abstract fun tearDown()

    @Before
    abstract fun setup()
}