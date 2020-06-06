package com.sadri.foursquare.data.models.venue_detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Sepehr Sadri on 6/6/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
data class VenueDetailResponseWrapper(
    @Expose
    @SerializedName("response")
    val venueDetailResponse: VenueDetailResponse
)