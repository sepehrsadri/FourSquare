package com.sadri.foursquare.data.repositories.explore

import com.sadri.foursquare.data.models.explore.ExploreResponseWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
interface ExploreServices {

    @GET("explore")
    suspend fun getExplores(
        @QueryMap queries: Map<String, String>,
        @Query("ll") location: String,
        @Query("v") apiVersion: Int,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<ExploreResponseWrapper>
}
