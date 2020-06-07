package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.venue_detail

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.sadri.foursquare.data.repositories.venue_detail.VenueDetailApiDataSource
import com.sadri.foursquare.data.repositories.venue_detail.VenueDetailPersistentDataSource
import com.sadri.foursquare.data.repositories.venue_detail.VenueDetailSingleSourceOfTruth
import com.sadri.foursquare.data.utils.ApiResult
import com.sadri.foursquare.data.utils.Result
import com.sadri.foursquare.general.Behavior
import com.sadri.foursquare.general.TestCase
import com.sadri.foursquare.general.getOrAwaitValue
import com.sadri.foursquare.models.venue.Location
import com.sadri.foursquare.models.venue.category.Category
import com.sadri.foursquare.models.venue.category.Icon
import com.sadri.foursquare.models.venue.detail.VenueDetail
import com.sadri.foursquare.ui.navigation.NavigationCommand
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Created by Sepehr Sadri on 6/7/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
internal class VenueDetailViewModelTest : TestCase() {
    private lateinit var venueDetailViewModel: VenueDetailViewModel
    private lateinit var spiedVenueDetailViewModel: VenueDetailViewModel

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

    override fun tearDown() {
    }

    override fun setup() {
        val venueDataSingleSourceOfTruth = VenueDetailSingleSourceOfTruth(
            venueApiDataSource,
            venuePersistentDataSource
        )

        venueDetailViewModel = VenueDetailViewModel(venueDataSingleSourceOfTruth)
        spiedVenueDetailViewModel = spy(venueDetailViewModel)
    }

    @Behavior
    @Test
    fun `given exist venue id when fetchVenueDetail then venueDetail contain response`() {
        // Given
        val id = SUCCESS_VENUE_ID

        // When
        venueDetailViewModel.fetchVenueDetail(id)

        // Then
        val venueDetail = venueDetailViewModel.venueDetail.getOrAwaitValue()

        assertTrue(
            successVenueDetailResponse.name == venueDetail.name
        )

        val category =
            venueDetail.category
                    as
                    com.sadri.foursquare.ui.screens.dashboard
                    .fragments.dashboard.venue_detail.Category.Available

        assertTrue(
            successVenueDetailResponse.categories.first().name == category.value
        )
    }

    @Behavior
    @Test
    fun `given venueId when venueDetailApiDataSource return error then navigate back`() {
        // Given
        val id = ERROR_VENUE_ID

        // When
        venueDetailViewModel.fetchVenueDetail(id)

        // Then
        assertTrue(
            venueDetailViewModel.navigationCommands.getOrAwaitValue() is NavigationCommand.Back
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