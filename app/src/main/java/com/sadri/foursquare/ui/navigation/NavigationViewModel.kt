package com.sadri.foursquare.ui.navigation

import androidx.navigation.NavDirections
import com.sadri.foursquare.ui.utils.BaseViewModel
import com.sadri.foursquare.utils.live_data.SingleLiveEvent

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Suppress("unused")
abstract class NavigationViewModel : BaseViewModel() {
    val navigationCommands: SingleLiveEvent<NavigationCommand> =
        SingleLiveEvent()

    fun navigate(directions: NavDirections) {
        navigationCommands.postValue(NavigationCommand.To(directions))
    }

    fun navigate(command: NavigationCommand) {
        navigationCommands.postValue(command)
    }
}