package com.sadri.foursquare.data.utils

import com.sadri.foursquare.models.MyPoint

/**
 * Created by Sepehr Sadri on 6/5/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
data class LocationResult(val status: Status, val data: MyPoint) {

    enum class Status {
        EMPTY,
        SAME,
        CHANGED
    }
}