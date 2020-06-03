package com.sadri.foursquare.models.venue

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
data class Location(
    @Expose
    @SerializedName("lat")
    val lat: Double,
    @Expose
    @SerializedName("lng")
    val lng: Double,
    @Expose
    @SerializedName("distance")
    val distance: Long,
    @Expose
    @SerializedName("formattedAddress")
    val address: List<String>
)