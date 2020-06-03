package com.sadri.foursquare.data.persistent.venue

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.sadri.foursquare.data.persistent.BaseDao
import com.sadri.foursquare.models.venue.Venue

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Dao
interface ExploreDao : BaseDao<Venue> {
    @Query("SELECT * FROM Venue LIMIT :limit OFFSET :offset")
    fun getByPage(limit: Int, offset: Int): LiveData<List<Venue>>

    @Query("SELECT * FROM Venue LIMIT :limit OFFSET :offset")
    suspend fun getByPageInstantly(limit: Int, offset: Int): List<Venue>

    @Query("SELECT * FROM Venue")
    fun getAll(): LiveData<List<Venue>>

    @Query("SELECT * FROM Venue")
    suspend fun getAllInstantly(): List<Venue>

    @Query("DELETE FROM Venue")
    suspend fun clear()
}