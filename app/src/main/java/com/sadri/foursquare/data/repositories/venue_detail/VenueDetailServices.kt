package com.sadri.foursquare.data.repositories.venue_detail

import com.sadri.foursquare.data.models.venue_detail.VenueDetailResponseWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by Sepehr Sadri on 6/6/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
interface VenueDetailServices {

    @GET("{VENUE_ID}")
    suspend fun getVenueDetail(
        @Path("VENUE_ID") venueId: String,
        @QueryMap queries: Map<String, String>,
        @Query("v") apiVersion: Int
    ): Response<VenueDetailResponseWrapper>
}