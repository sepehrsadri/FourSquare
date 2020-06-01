package com.sadri.foursquare.ui.screens.requirement_satisfier

import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.Task
import com.sadri.foursquare.ui.utils.owner_view_model.BaseViewModelOwner

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
interface RequirementSatisfierViewModelOwner : BaseViewModelOwner {
    fun getLocationSettingTask(): Task<LocationSettingsResponse>
}