package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.venue_detail

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.sadri.foursquare.data.repositories.venue_detail.VenueDetailApiDataSource
import com.sadri.foursquare.data.repositories.venue_detail.VenueDetailPersistentDataSource
import com.sadri.foursquare.data.repositories.venue_detail.VenueDetailSingleSourceOfTruth
import com.sadri.foursquare.data.utils.ApiResult
import com.sadri.foursquare.data.utils.Result
import com.sadri.foursquare.general.TestCase
import com.sadri.foursquare.general.TestDispatcher
import com.sadri.foursquare.general.getOrAwaitValue
import com.sadri.foursquare.models.venue.Location
import com.sadri.foursquare.models.venue.category.Category
import com.sadri.foursquare.models.venue.category.Icon
import com.sadri.foursquare.models.venue.detail.VenueDetail
import com.sadri.foursquare.ui.utils.mvi.BaseState
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Created by Sepehr Sadri on 6/7/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
internal class VenueDetailViewModelTest : TestCase() {
    private lateinit var venueDetailViewModel: VenueDetailMviViewModel

    private val venuePersistentDataSource = mock<VenueDetailPersistentDataSource> {
        onBlocking { getByIdInstantly(SUCCESS_VENUE_ID) } doReturn Result.Success(
            successVenueDetailResponse
        )
        onBlocking { getByIdInstantly(ERROR_VENUE_ID) } doReturn Result.Empty
    }

    private val venueApiDataSource = mock<VenueDetailApiDataSource>() {
        onBlocking { getVenueDetail(ERROR_VENUE_ID) } doReturn ApiResult.Error(
            com.sadri.foursquare.models.Error(404, "permitted request")
        )
    }

    private val viewModelObserver = Observer<VenueDetailViewState> {}

    override fun tearDown() {
        venueDetailViewModel.viewStates().removeObserver(viewModelObserver)
    }

    override fun setup() {
        val venueDataSingleSourceOfTruth = VenueDetailSingleSourceOfTruth(
            venueApiDataSource,
            venuePersistentDataSource,
            TestDispatcher
        )

        venueDetailViewModel = VenueDetailMviViewModel(venueDataSingleSourceOfTruth)

        venueDetailViewModel.viewStates().observeForever(viewModelObserver)
    }

    @Test
    fun `given fetch intent when venueDetailApiDataSource success then state contain response`() {
        // Given
        val intent = VenueDetailIntent.Fetch(SUCCESS_VENUE_ID)

        // When
        venueDetailViewModel.dispatch(intent)

        // Then
        val state = venueDetailViewModel.viewStates().getOrAwaitValue()
        val result = state.result

        assertTrue(
            state.base.stable
        )

        assertTrue(
            successVenueDetailResponse.name == result.name
        )

        val category =
            result.category
                    as
                    com.sadri.foursquare.ui.screens.dashboard
                    .fragments.dashboard.venue_detail.Category.Available

        assertTrue(
            successVenueDetailResponse.categories.first().name == category.value
        )
    }

    @Test
    fun `given fetch intent when venueDetailApiDataSource return error then state is show toast`() {
        // Given
        val intent = VenueDetailIntent.Fetch(ERROR_VENUE_ID)

        // When
        venueDetailViewModel.dispatch(intent)

        // Then
        val state = venueDetailViewModel.viewStates().getOrAwaitValue()

        assertFalse(
            state.base.stable
        )
        assertTrue(
            state.base.message !is BaseState.Message.Disabled
        )
        assertFalse(
            state.base.showLoading
        )
    }

    companion object {
        private const val SUCCESS_VENUE_ID = "1234"
        private const val ERROR_VENUE_ID = "6789"

        private val locationFake = Location(
            230.0,
            35.727954,
            51,
            listOf("Germany", "Berlin")
        )

        private val categoryFake: List<Category> = listOf(
            Category(
                "1234",
                "Cafe",
                "Cafe",
                Icon(
                    "https://",
                    "google.com"
                )
            )
        )

        private val successVenueDetailResponse = VenueDetail(
            SUCCESS_VENUE_ID,
            "Success",
            locationFake,
            categoryFake,
            null,
            null,
            null,
            null,
            null,
            null
        )
    }
}