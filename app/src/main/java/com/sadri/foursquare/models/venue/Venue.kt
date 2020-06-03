package com.sadri.foursquare.models.venue

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sadri.foursquare.models.venue.category.Category

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
data class Venue(
    @Expose
    @SerializedName("id")
    val id: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("location")
    val location: Location,
    @Expose
    @SerializedName("categories")
    val categories: List<Category>
)