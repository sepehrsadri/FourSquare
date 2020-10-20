package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.explore

import com.sadri.foursquare.models.venue.Venue

interface DashboardListAdapterContract {
    fun onVenueClick(venue: Venue)
}