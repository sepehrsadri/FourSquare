package com.sadri.foursquare.models

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
data class MyPoint(
    val lat: Double,
    val lng: Double
) {
    override fun toString(): String {
        return "$lat,$lng"
    }
}