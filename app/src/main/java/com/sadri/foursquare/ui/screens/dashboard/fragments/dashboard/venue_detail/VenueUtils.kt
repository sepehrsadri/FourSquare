package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.venue_detail

import com.sadri.foursquare.models.venue.detail.VenueDetail
import com.sadri.foursquare.utils.toMyPoint

fun convertToUiDataModel(
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

    val direction: Direction =
        if (data.location != null) {
            Direction.Available(
                data.location.address.joinToString(","),
                data.location.toMyPoint()
            )
        } else {
            Direction.Empty
        }

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
        data.formattedName,
        category,
        rate,
        availabilityStatus,
        contactStatus,
        direction,
        description,
        like
    )
}