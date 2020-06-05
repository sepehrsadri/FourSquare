package com.sadri.foursquare.utils.mappers

import android.location.Location
import com.sadri.foursquare.models.MyPoint

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
fun Location.toMyPoint(): MyPoint {
    return MyPoint(this.latitude, this.longitude)
}