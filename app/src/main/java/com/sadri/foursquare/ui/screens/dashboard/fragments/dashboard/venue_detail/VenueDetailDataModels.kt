package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.venue_detail

import com.sadri.foursquare.R
import com.sadri.foursquare.models.MyPoint

/**
 * Created by Sepehr Sadri on 6/6/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
data class VenueDetailDataModel(
    val name: String,
    val category: Category,
    val rate: Rate,
    val availabilityStatus: AvailabilityStatus,
    val phoneContact: PhoneContact,
    val direction: Direction,
    val description: Description,
    val likes: Like
) {
    companion object {
        fun stable(): VenueDetailDataModel {
            return VenueDetailDataModel(
                "",
                Category.Empty,
                Rate.Empty,
                AvailabilityStatus.Undefined,
                PhoneContact.Disabled,
                Direction.Empty,
                Description.Empty,
                Like.Empty
            )
        }
    }
}

sealed class AvailabilityStatus(
    val icon: Int,
    val status: String?,
    val defaultValue: Int = R.string.undefined_status
) {
    data class Open(val text: String) : AvailabilityStatus(R.drawable.ic_is_open, text)
    data class Close(val text: String) : AvailabilityStatus(R.drawable.ic_is_closed, text)
    object Undefined : AvailabilityStatus(R.drawable.ic_is_undefined, null)
}

sealed class PhoneContact {
    data class Enabled(val number: String) : PhoneContact()
    object Disabled : PhoneContact()
}

sealed class Direction {
    data class Available(
        val address: String,
        val myPoint: MyPoint
    ) : Direction()

    object Empty : Direction()
}

sealed class Description(val defaultText: Int = R.string.empty_description) {
    data class Available(val text: String) : Description()
    object Empty : Description()
}

sealed class Rate(val defaultText: Int = R.string.undefined_rate) {
    data class Available(val value: Float) : Rate()
    object Empty : Rate()
}

sealed class Like(val defaultText: Int = R.string.undefined_rate) {
    data class Available(val value: Long) : Like()
    object Empty : Like()
}

sealed class Category(val defaultText: Int = R.string.unknown_category) {
    data class Available(val value: String) : Category()
    object Empty : Category()
}