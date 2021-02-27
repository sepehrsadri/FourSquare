package com.sadri.foursquare.models.venue

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sadri.foursquare.models.venue.category.Category
import com.sadri.foursquare.utils.extractEnDigits

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Entity(tableName = "Venue")
data class Venue(
    @PrimaryKey
    @Expose
    @SerializedName("id")
    var id: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("location")
    val location: Location,
    @Expose
    @SerializedName("categories")
    val categories: List<Category>
) {
    val formattedName: String
        get() = name.extractEnDigits()
}