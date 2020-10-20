package com.sadri.foursquare.ui.utils.mvi.model

import com.sadri.foursquare.ui.utils.mvi.BaseState

interface BaseViewState : MviViewState {
    val base : BaseState
}