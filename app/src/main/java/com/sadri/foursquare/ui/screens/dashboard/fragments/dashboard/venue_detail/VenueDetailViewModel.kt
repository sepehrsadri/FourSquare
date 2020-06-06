package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.venue_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sadri.foursquare.data.repositories.venue_detail.VenueDetailSingleSourceOfTruth
import com.sadri.foursquare.data.utils.Result
import com.sadri.foursquare.models.venue.detail.VenueDetail
import com.sadri.foursquare.ui.navigation.NavigationCommand
import com.sadri.foursquare.ui.navigation.NavigationViewModel
import com.sadri.foursquare.utils.extractEnDigits
import com.sadri.foursquare.utils.live_data.SingleLiveEvent
import com.sadri.foursquare.utils.toMyPoint
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

    private fun convertToUiDataModel(
        data: VenueDetail
    ): VenueDetailDataModel {
        val isOpenStatus = data.hours

        val availabilityStatus =
            if (isOpenStatus != null) {
                if (isOpenStatus.status == null || isOpenStatus.isOpen == null) {
                    AvailabilityStatus.Undefined
                } else {
                    if (isOpenStatus.isOpen) {
                        AvailabilityStatus.Open(isOpenStatus.status)
                    } else {
                        AvailabilityStatus.Close(isOpenStatus.status)
                    }
                }
            } else {
                AvailabilityStatus.Undefined
            }

        val contactData = data.contact

        val contactStatus =
            if (contactData?.phone != null) {
                PhoneContact.Enabled(contactData.phone)
            } else {
                PhoneContact.Disabled
            }

        val direction = Direction(
            data.location.address.joinToString(","),
            data.location.toMyPoint()
        )

        val rate =
            if (data.rating != null) {
                Rate.Available(data.rating.toFloat() / 2.0F)
            } else {
                Rate.Empty
            }

        val description =
            if (data.description != null) {
                Description.Available(data.description)
            } else {
                Description.Empty
            }

        val like =
            if (data.like?.count != null) {
                Like.Available(data.like.count)
            } else {
                Like.Empty
            }

        val category =
            if (data.categories.firstOrNull()?.name != null) {
                Category.Available(data.categories.firstOrNull()!!.name)
            } else {
                Category.Empty
            }

        return VenueDetailDataModel(
            data.name.extractEnDigits(),
            category,
            rate,
            availabilityStatus,
            contactStatus,
            direction,
            description,
            like
        )
    }

    fun onBackButtonClick() {
        navigate(
            NavigationCommand.Back
        )
    }
}