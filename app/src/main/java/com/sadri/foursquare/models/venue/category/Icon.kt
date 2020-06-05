package com.sadri.foursquare.models.venue.category

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
data class Icon(
    @Expose
    @SerializedName("prefix")
    val prefix: String,
    @Expose
    @SerializedName("suffix")
    val suffix: String
) {
    val url = prefix + suffix
}