package com.sadri.foursquare.data.repositories.venue_detail

import com.sadri.foursquare.data.api.APIUtils
import com.sadri.foursquare.data.utils.ApiErrorHandler
import com.sadri.foursquare.data.utils.BaseAPIDataSource
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
class VenueDetailApiDataSource @Inject constructor(
    private val services: VenueDetailServices,
    errorHandler: ApiErrorHandler,
    dispatcher: DispatcherProvider
) : BaseAPIDataSource(errorHandler, dispatcher) {

    suspend fun getVenueDetail(
        venueId: String
    ) = getResult {
        services.getVenueDetail(
            venueId,
            APIUtils.queries,
            APIUtils.API_DATE_VERSION
        )
    }
}