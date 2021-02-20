package com.sadri.foursquare.data.repositories.venue_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.liveData
import com.sadri.foursquare.data.utils.ApiResult
import com.sadri.foursquare.data.utils.Result
import com.sadri.foursquare.models.venue.detail.VenueDetail
import com.sadri.foursquare.ui.utils.DispatcherProvider
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Sepehr Sadri on 6/6/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Singleton
class VenueDetailSingleSourceOfTruth @Inject constructor(
    private val venueDetailApiDataSource: VenueDetailApiDataSource,
    val venueDetailPersistentDataSource: VenueDetailPersistentDataSource,
    private val dispatcher: DispatcherProvider
) {
    /**
     * This is right implementation of single source strategy because after fetching from api should update last data
     */
    fun fetchVenueDetail(
        venueId: String
    ): LiveData<Result<VenueDetail>> =
        liveData(dispatcher.ui()) {
            emit(Result.Loading)

            emitSource(venueDetailPersistentDataSource.getById(venueId))

            apiRequest(venueId)
        }

    private suspend fun LiveDataScope<Result<VenueDetail>>.apiRequest(
        venueId: String,
        emitOnSuccess: Boolean = false
    ) {
        when (val apiRes = venueDetailApiDataSource.getVenueDetail(venueId)) {
            is ApiResult.Success -> {
                val data = apiRes.data
                if (data != null) {
                    val venueDetail = data.venueDetailResponse.venueDetail
                    venueDetailPersistentDataSource.save(venueDetail)
                    if (emitOnSuccess) {
                        emit(Result.Success(venueDetail))
                    }
                }
            }
            is ApiResult.Error -> {
                emit(Result.Error(apiRes.error))
            }
        }
    }

    /**
     * Due to FourSquare premium request if data offline is exist we won't request api
     * https://developer.foursquare.com/docs/api-reference/venues/details/
     */
    fun limitedFetchVenueDetail(
        venueId: String
    ): LiveData<Result<VenueDetail>> =
        liveData(dispatcher.ui()) {
            emit(Result.Loading)

            val source = venueDetailPersistentDataSource.getByIdInstantly(venueId)

            if (source is Result.Success) {
                emit(source)
                return@liveData
            }

            apiRequest(venueId, true)
        }
}