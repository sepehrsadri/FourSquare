package com.sadri.foursquare.models.venue.detail

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sadri.foursquare.models.venue.Location
import com.sadri.foursquare.models.venue.category.Category

/**
 * Created by Sepehr Sadri on 6/6/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Entity(tableName = "VenueDetail")
data class VenueDetail(
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
    val categories: List<Category>,
    @Expose
    @SerializedName("canonicalUrl")
    val canonicalUrl: String?,
    @Expose
    @SerializedName("rating")
    val rating: Double,
    @Expose
    @SerializedName("contact")
    val contact: Contact?,
    @Expose
    @SerializedName("likes")
    val like: Like?,
    @Expose
    @SerializedName("description")
    val description: String?,
    @Expose
    @SerializedName("hours")
    val hours: Hour?
)