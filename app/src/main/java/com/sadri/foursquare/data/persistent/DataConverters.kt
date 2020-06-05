package com.sadri.foursquare.data.persistent

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sadri.foursquare.models.venue.Location
import com.sadri.foursquare.models.venue.category.Category

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
class DataConverters {
    @TypeConverter
    fun fromLocation(value: Location): String {
        val gson = Gson()
        val type = object : TypeToken<Location>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toLocation(value: String): Location {
        val gson = Gson()
        val type = object : TypeToken<Location>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromCategories(value: List<Category>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Category>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCategories(value: String): List<Category> {
        val gson = Gson()
        val type = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson(value, type)
    }
}