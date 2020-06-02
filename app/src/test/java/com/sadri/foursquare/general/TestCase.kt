package com.sadri.foursquare.general

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.MockitoAnnotations

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@ExtendWith(InstantExecutorExtension::class)
internal abstract class TestCase {

    init {
        MockitoAnnotations.initMocks(this)
    }

    @AfterEach
    abstract fun tearDown()

    @BeforeEach
    abstract fun setup()
}