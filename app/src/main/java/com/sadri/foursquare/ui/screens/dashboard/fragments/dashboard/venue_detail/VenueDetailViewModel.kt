package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.venue_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sadri.foursquare.data.repositories.venue_detail.VenueDetailSingleSourceOfTruth
import com.sadri.foursquare.data.utils.Result
import com.sadri.foursquare.models.venue.detail.VenueDetail
import com.sadri.foursquare.ui.navigation.NavigationCommand
import com.sadri.foursquare.ui.navigation.NavigationViewModel
import com.sadri.foursquare.utils.live_data.SingleLiveEvent
import javax.inject.Inject

/**
 * Created by Sepehr Sadri on 6/6/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
class VenueDetailViewModel @Inject constructor(
    private val venueDetailSingleSourceOfTruth: VenueDetailSingleSourceOfTruth
) : NavigationViewModel() {
    val messageEvent = SingleLiveEvent<String>()

    private val _venueDetail = MutableLiveData<VenueDetailDataModel>()
    val venueDetail: LiveData<VenueDetailDataModel>
        get() = _venueDetail

    private var isLoading = false

    private val venueDetailObserver =
        Observer<Result<VenueDetail>> {
            when (it) {
                is Result.Loading -> {
                    fullscreenLoading.value = true
                }
                is Result.Success -> {
                    _venueDetail.value = convertToUiDataModel(it.data)

                    onDataLoaded()
                }
                is Result.Error -> {
                    onDataLoaded()

                    if (_venueDetail.value == null) {
                        messageEvent.value = it.error.message

                        onBackButtonClick()
                    }
                }
            }
        }

    private fun onDataLoaded() {
        fullscreenLoading.value = false
        isLoading = false
    }

    fun fetchVenueDetail(venueId: String) {
        if (isLoading) {
            return
        }
        isLoading = true
        observe(
            venueDetailSingleSourceOfTruth.limitedFetchVenueDetail(venueId),
            venueDetailObserver
        )
    }


    fun onBackButtonClick() {
        navigate(
            NavigationCommand.Back
        )
    }
}