package com.sadri.foursquare.utils

import android.location.Location
import android.location.LocationManager
import com.sadri.foursquare.models.MyPoint

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
fun Location.toMyPoint(): MyPoint {
    return MyPoint(
        this.latitude,
        this.longitude
    )
}

fun Location.isSame(loc: Location): Boolean {
    return this.latitude == loc.latitude &&
            this.longitude == loc.longitude
}

fun MyPoint.toLocation(): Location {
    val location =
        Location(LocationManager.GPS_PROVIDER)
    location.latitude = this.lat
    location.longitude = this.lng
    return location
}

fun com.sadri.foursquare.models.venue.Location.toMyPoint(): MyPoint {
    return MyPoint(
        this.lat,
        this.lng
    )
}