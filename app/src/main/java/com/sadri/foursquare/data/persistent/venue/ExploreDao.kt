package com.sadri.foursquare.data.persistent.venue

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.sadri.foursquare.data.persistent.BaseDao
import com.sadri.foursquare.data.repositories.explore.EXPLORE_DATA_OFFSET
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

    @Transaction
    suspend fun dropAndInsertAll(explores: List<Venue>) {
        clear()
        insertAll(explores)
    }

    @Transaction
    suspend fun insertByPaged(offset: Int, explores: List<Venue>) {
        val daoVenueListSize = getAllInstantly()
        val currentDaoSize = daoVenueListSize.size

        if (currentDaoSize >= offset + EXPLORE_DATA_OFFSET) {
            val resultList: MutableList<Venue> = ArrayList()

            if (offset > 0) {
                resultList.addAll(daoVenueListSize.subList(0, offset + EXPLORE_DATA_OFFSET))
                resultList.addAll(explores)
            } else {
                resultList.addAll(explores)
            }

            if (currentDaoSize > offset + EXPLORE_DATA_OFFSET) {
                resultList.addAll(explores)
            }

            if (currentDaoSize > resultList.size) {
                resultList.addAll(daoVenueListSize.subList(resultList.size, currentDaoSize))
            }

            clear()

            insertAll(resultList)
        } else {
            insertAll(explores)
        }
    }
}