package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.venue_detail.mvi

import com.sadri.foursquare.models.venue.detail.VenueDetail
import com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.venue_detail.VenueDetailDataModel
import com.sadri.foursquare.ui.utils.mvi.BaseState
import com.sadri.foursquare.ui.utils.mvi.BaseViewState
import com.sadri.foursquare.ui.utils.mvi.MviIntent
import com.sadri.foursquare.ui.utils.mvi.MviResult

data class VenueDetailViewState(
    override val base: BaseState,
    val result: VenueDetailDataModel
) : BaseViewState {
    companion object {
        fun idle(): VenueDetailViewState {
            return VenueDetailViewState(
                BaseState.stable(),
                VenueDetailDataModel.stable()
            )
        }
    }
}

sealed class VenueDetailResult : MviResult {
    data class Error(val message: String) : VenueDetailResult()
    data class Success(val result: VenueDetail) : VenueDetailResult()
    object Loading : VenueDetailResult()
}

sealed class VenueDetailIntent : MviIntent {
    data class Fetch(val id: String) : VenueDetailIntent()
}