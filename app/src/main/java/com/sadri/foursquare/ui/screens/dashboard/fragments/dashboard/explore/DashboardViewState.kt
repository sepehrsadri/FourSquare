package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.explore

import com.sadri.foursquare.models.venue.Venue
import com.sadri.foursquare.ui.utils.mvi.BaseState
import com.sadri.foursquare.ui.utils.mvi.model.BaseViewState
import com.sadri.foursquare.ui.utils.mvi.model.MviIntent
import com.sadri.foursquare.ui.utils.mvi.model.MviResult

data class DashboardViewState(
    override val base: BaseState,
    val venueListChanged: Boolean = false,
    val venuesList: List<Venue> = ArrayList(),
    val venuesListAvailability: Boolean = true
) : BaseViewState {
    companion object {
        fun idle(): DashboardViewState = DashboardViewState(
            base = BaseState.stable()
        )
    }
}

sealed class DashboardResult : MviResult {
    object Loading : DashboardResult()
    data class VenueListFetched(val venues: List<Venue>) : DashboardResult()
    data class Error(val message: String) : DashboardResult()
    data class ResMsg(val message: Int) : DashboardResult()
}


sealed class DashboardIntent : MviIntent {
    object Scroll : DashboardIntent()
    object RefreshExplores : DashboardIntent()
    object Init : DashboardIntent()
}