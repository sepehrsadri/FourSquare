package com.sadri.foursquare.data.models.explore

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
data class ExploreResponse(
    @Expose
    @SerializedName("groups")
    val groups: List<Group>
)