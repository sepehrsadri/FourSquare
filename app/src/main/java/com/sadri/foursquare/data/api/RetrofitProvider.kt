package com.sadri.foursquare.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
object RetrofitProvider {

    fun getRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()

        APIUtils.getLoggingInterceptor()?.let {
            httpClient.addInterceptor(
                it
            )
        }

        val builder = Retrofit.Builder()
            .baseUrl(
                APIUtils.API_BASE_URL
            ).addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(
                httpClient.build()
            )

        return builder.build()
    }

    fun <T> provideService(
        retrofit: Retrofit,
        clazz: Class<T>
    ): T {
        return retrofit.create(clazz)
    }
}