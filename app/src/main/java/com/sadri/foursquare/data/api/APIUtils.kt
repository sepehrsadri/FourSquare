package com.sadri.foursquare.data.api

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
object APIUtils {
    const val API_BASE_URL = "https://api.foursquare.com/v2/"

    fun getLoggingInterceptor(): Interceptor? {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        return logger
    }
}