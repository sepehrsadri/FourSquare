package com.sadri.foursquare.data.persistent.venue_detail

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.sadri.foursquare.data.persistent.BaseDao
import com.sadri.foursquare.models.venue.detail.VenueDetail

/**
 * Created by Sepehr Sadri on 6/6/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Dao
interface VenueDetailDao : BaseDao<VenueDetail> {
    @Query("DELETE FROM VenueDetail")
    suspend fun clear()

    @Query("SELECT * FROM VenueDetail WHERE id=:id ")
    fun getById(id: String): LiveData<VenueDetail?>

    @Query("SELECT * FROM VenueDetail WHERE id=:id ")
    suspend fun getByIdInstantly(id: String): VenueDetail?
}