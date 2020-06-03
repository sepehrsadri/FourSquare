package com.sadri.foursquare.data.utils

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
sealed class Result<out T> {
    object Loading : Result<Nothing>()
    object Empty : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val error: com.sadri.foursquare.models.Error) : Result<Nothing>()

    companion object {
        fun isLoading(result: Result<*>?): Boolean {
            return result is Loading
        }
    }
}
