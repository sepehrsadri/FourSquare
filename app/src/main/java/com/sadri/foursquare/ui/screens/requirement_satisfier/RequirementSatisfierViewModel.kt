package com.sadri.foursquare.ui.screens.requirement_satisfier

import com.sadri.foursquare.components.permission.PermissionProvider
import com.sadri.foursquare.ui.utils.owner_view_model.OwnerViewModel
import javax.inject.Inject

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
class RequirementSatisfierViewModel @Inject constructor(
    private val permissionProvider: PermissionProvider
) : OwnerViewModel<RequirementSatisfierViewModelOwner>()