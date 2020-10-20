package com.sadri.foursquare.ui.screens.requirement_satisfier

import com.google.android.gms.common.api.ResolvableApiException
import com.sadri.foursquare.ui.utils.mvi.BaseState
import com.sadri.foursquare.ui.utils.mvi.model.BaseViewState
import com.sadri.foursquare.ui.utils.mvi.model.MviIntent
import com.sadri.foursquare.ui.utils.mvi.model.MviResult

data class RequirementSatisfierViewState(
    override val base: BaseState = BaseState.stable(),
    val dataModel: RequirementSatisfierDataModel = RequirementSatisfierDataModel.Empty,
    val requestPermission: RequestPermission = RequestPermission.Nothing
) : BaseViewState

sealed class RequestPermission {
    data class Location(val exception: ResolvableApiException) : RequestPermission()
    object GPS : RequestPermission()
    object Nothing : RequestPermission()
}

sealed class RequirementSatisfierIntent : MviIntent {
    object RequestPermissionAndLocationSwitch : RequirementSatisfierIntent()
}

sealed class RequirementSatisfierResult : MviResult {
    object RequestGps : RequirementSatisfierResult()
    data class RequestLocation(val exception: ResolvableApiException) : RequirementSatisfierResult()
    data class Toast(val message: Int) : RequirementSatisfierResult()
    data class Result(val res: RequirementSatisfierDataModel) : RequirementSatisfierResult()
}