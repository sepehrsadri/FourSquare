package com.sadri.foursquare.data.repositories.explore

import com.sadri.foursquare.data.persistent.venue.ExploreDao
import com.sadri.foursquare.data.utils.Result
import com.sadri.foursquare.models.venue.Venue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Singleton
class ExplorePersistentDataSource @Inject constructor(
    private val exploreDao: ExploreDao
) {
    suspend fun getExploresByPageInstantly(offset: Int): Result<List<Venue>> =
        withContext(Dispatchers.IO) {
            val data = exploreDao.getByPageInstantly(
                EXPLORE_DATA_LOAD_LIMIT,
                offset
            )
            if (data.isNullOrEmpty().not()) {
                Result.Success(data)
            } else {
                Result.Empty
            }
        }

    suspend fun saveAll(venues: List<Venue>) =
        withContext(Dispatchers.IO) {
            exploreDao.insertAll(venues)
        }

    suspend fun clear() =
        withContext(Dispatchers.IO) {
            exploreDao.clear()
        }

    suspend fun dropAndInsertAll(venues: List<Venue>) =
        withContext(Dispatchers.IO) {
            exploreDao.dropAndInsertAll(venues)
        }

    suspend fun insertByPage(offset: Int, venues: List<Venue>) =
        withContext(Dispatchers.IO) {
            exploreDao.insertByPaged(offset, venues)
        }
}