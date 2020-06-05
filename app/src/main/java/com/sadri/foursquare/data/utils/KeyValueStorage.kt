package com.sadri.foursquare.data.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.sadri.foursquare.BuildConfig
import com.sadri.foursquare.models.MyPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
class KeyValueStorage @Inject constructor(private val sharedPreferences: SharedPreferences) {
    companion object {
        private const val SHARED_PREFERENCES_NAME =
            "${BuildConfig.APPLICATION_ID}.local_key_value_storage"

        fun getPrivateSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(
                SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
        }
    }

    fun getBool(key: String, defaultValue: Boolean = false) =
        sharedPreferences.getBoolean(key, defaultValue)

    fun putBool(key: String, value: Boolean) {
        sharedPreferences.edit(true) {
            putBoolean(key, value)
        }
    }

    suspend fun getString(key: String, defaultValue: String? = null): String? =
        withContext(Dispatchers.IO) {
            sharedPreferences.getString(key, defaultValue)
        }

    suspend fun putString(key: String, value: String?) = withContext(Dispatchers.IO) {
        sharedPreferences.edit(true) {
            putString(key, value)
        }
    }

    suspend fun getMyPoint(key: String, defaultValue: String? = null): MyPoint? =
        withContext(Dispatchers.IO) {
            val gson = Gson()
            val json = sharedPreferences.getString(key, defaultValue)
            gson.fromJson(
                json,
                MyPoint::class.java
            )
        }

    suspend fun putMyPoint(key: String, value: MyPoint) =
        withContext(Dispatchers.IO) {
            val gson = Gson()
            val json = gson.toJson(value)
            sharedPreferences.edit(true) {
                putString(key, json)
            }
        }
}