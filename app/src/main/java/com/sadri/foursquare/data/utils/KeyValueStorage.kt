package com.sadri.foursquare.data.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.sadri.foursquare.BuildConfig
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
}