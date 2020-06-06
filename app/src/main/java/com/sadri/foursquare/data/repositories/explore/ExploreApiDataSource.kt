package com.sadri.foursquare.data.repositories.explore

import com.sadri.foursquare.data.api.APIUtils
import com.sadri.foursquare.data.utils.ApiErrorHandler
import com.sadri.foursquare.data.utils.BaseAPIDataSource
import com.sadri.foursquare.models.MyPoint
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Singleton
class ExploreApiDataSource @Inject constructor(
    private val service: ExploreServices,
    errorHandler: ApiErrorHandler
) : BaseAPIDataSource(errorHandler) {

    suspend fun getExplores(model: ExploreServiceModel) = getResult {
        service.getExplores(
            APIUtils.queries,
            model.point,
            APIUtils.API_DATE_VERSION,
            model.offset,
            EXPLORE_DATA_LOAD_LIMIT,
            model.distanceSort,
            model.popularitySort
        )
    }
}

data class ExploreServiceModel(
    private val myPoint: MyPoint,
    val offset: Int,
    private val sortByDistance: Boolean = true,
    private val sortByPopularity: Boolean = false
) {
    val point = myPoint.toString()
    val distanceSort = if (sortByDistance) 1 else 0
    val popularitySort = if (sortByPopularity) 1 else 0
}