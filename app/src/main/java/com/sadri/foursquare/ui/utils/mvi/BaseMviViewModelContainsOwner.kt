package com.sadri.foursquare.ui.utils.mvi

import com.sadri.foursquare.ui.utils.mvi.model.BaseViewState
import com.sadri.foursquare.ui.utils.mvi.model.MviIntent
import com.sadri.foursquare.ui.utils.mvi.model.MviResult
import com.sadri.foursquare.ui.utils.mvi.model.ViewModelOwner

abstract class BaseMviViewModelContainsOwner<OWNER : ViewModelOwner, STATE : BaseViewState, INTENT : MviIntent, RESULT : MviResult>(
    initialState: STATE
) :
    BaseMviViewModel<STATE, INTENT, RESULT>(initialState) {
    private var owner: OWNER? = null

    open fun onStart(owner: OWNER) {
        this.owner = owner
    }

    open fun onStop() {
        owner = null
    }

    fun getOwner(): OWNER? {
        return this.owner
    }

    fun hasOwner() = this.owner != null
}