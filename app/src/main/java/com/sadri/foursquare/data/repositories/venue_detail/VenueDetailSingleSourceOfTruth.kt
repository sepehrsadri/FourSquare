package com.sadri.foursquare.data.repositories.venue_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.sadri.foursquare.data.utils.ApiResult
import com.sadri.foursquare.data.utils.Result
import com.sadri.foursquare.models.venue.detail.VenueDetail
import kotlinx.coroutines.Dispatchers
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
    private val venueDetailPersistentDataSource: VenueDetailPersistentDataSource
) {
    fun fetchVenueDetail(
        venueId: String
    ): LiveData<Result<VenueDetail>> =
        liveData(Dispatchers.Main) {
            emit(Result.Loading)

            emitSource(venueDetailPersistentDataSource.getById(venueId))

            when (val apiRes = venueDetailApiDataSource.getVenueDetail(venueId)) {
                is ApiResult.Success -> {
                    val data = apiRes.data
                    if (data != null) {
                        val venueDetail = data.venueDetailResponse.venueDetail
                        venueDetailPersistentDataSource.save(venueDetail)
                    }
                }
                is ApiResult.Error -> {
                    emit(Result.Error(apiRes.error))
                }
            }
        }
}