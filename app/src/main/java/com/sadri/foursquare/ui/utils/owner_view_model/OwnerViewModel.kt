package com.sadri.foursquare.ui.utils.owner_view_model

import com.sadri.foursquare.ui.navigation.NavigationViewModel

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Suppress("unused")
abstract class OwnerViewModel<T : BaseViewModelOwner> : NavigationViewModel() {
    private var owner: T? = null

    open fun onStart(owner: T) {
        this.owner = owner
    }

    open fun onStop() {
        owner = null
    }

    fun getOwner(): T? = this.owner

    fun hasOwner() = this.owner != null
}