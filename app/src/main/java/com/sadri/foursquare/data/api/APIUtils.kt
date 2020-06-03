package com.sadri.foursquare.data.api

import com.sadri.foursquare.BuildConfig
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
object APIUtils {
    const val API_BASE_URL = "https://api.foursquare.com/v2/venues/"

    private const val CLIENT_ID: String = "CE0RSFN23WP1OMBUFCKWY4KDAZHCAHX1PWRTUDRLKJGYBDY3"

    private const val CLIENT_SECRET: String = "IVZ4YBYYW00NEA2PKVBW2DS10QB0R4EOF2M2KXT4TPNMOQUQ"

    val queries: HashMap<String, String> =
        hashMapOf(
            "client_id" to CLIENT_ID,
            "client_secret" to CLIENT_SECRET
        )

    const val API_DATE_VERSION: Int = 20200603


    fun getLoggingInterceptor(): Interceptor? {
        if (BuildConfig.DEBUG.not()) {
            return null
        }

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        return logger
    }
}