package com.sadri.foursquare.data.repositories.explore

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.liveData
import com.sadri.foursquare.components.location.LocationProvider
import com.sadri.foursquare.data.utils.ApiResult
import com.sadri.foursquare.data.utils.LocationResult
import com.sadri.foursquare.data.utils.Result
import com.sadri.foursquare.models.MyPoint
import com.sadri.foursquare.models.venue.Venue
import com.sadri.foursquare.ui.utils.DispatcherProvider
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Singleton
class ExploreDataSingleSourceOfTruth @Inject constructor(
    private val exploreApiDataSource: ExploreApiDataSource,
    val explorePersistentDataSource: ExplorePersistentDataSource,
    private val locationProvider: LocationProvider,
    private val dispatcher: DispatcherProvider
) {
    private val fetchedList: MutableList<Result<ExploreResult>> = ArrayList()

    fun fetchExplores(
        offset: Int
    ): LiveData<Result<ExploreResult>> =
        liveData(dispatcher.ui()) {
            emit(Result.Loading)

            val locationResult = locationProvider.getLastLocationResult()

            if (
                locationResult.status == LocationResult.Status.CHANGED
            ) {
                explorePersistentDataSource.clear()
            }

            val source = explorePersistentDataSource.getExploresByPageInstantly(offset)

            if (source is Result.Success) {
                emit(Result.Success(ExploreResult(source.data, offset)))
            }

            apiRequest(offset, locationResult.data)
        }

    private suspend fun LiveDataScope<Result<ExploreResult>>.apiRequest(
        offset: Int,
        myPoint: MyPoint
    ) {
        val apiRes = exploreApiDataSource.getExplores(
            ExploreServiceModel(
                myPoint,
                offset
            )
        )

        when (apiRes) {
            is ApiResult.Success -> {
                val data = apiRes.data
                if (data != null) {
                    val groups = data.exploreResponse.groups.firstOrNull()

                    if (groups != null && groups.items.isNullOrEmpty().not()) {
                        val venues: MutableList<Venue> = ArrayList()

                        groups.items.forEach {
                            venues.add(
                                it.venue
                            )
                        }
                        when {
                            venues.isNullOrEmpty().not() -> {
                                val result = Result.Success(ExploreResult(venues, offset, true))
                                fetchedList.add(result)
                                emit(result)
                                explorePersistentDataSource.insertByPage(offset, venues)
                            }
                        }
                    }
                }
            }
            is ApiResult.Error -> {
                val result = Result.Error(apiRes.error)
                fetchedList.add(result)
                emit(result)
            }
        }
    }

    @VisibleForTesting
    fun getFetchedList() = fetchedList
}

data class ExploreResult(
    val result: List<Venue>,
    val offset: Int,
    val reliable: Boolean = false
)