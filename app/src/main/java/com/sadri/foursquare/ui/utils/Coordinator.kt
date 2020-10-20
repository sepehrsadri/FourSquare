package com.sadri.foursquare.ui.utils

import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import com.sadri.foursquare.ui.navigation.NavigationCommand
import com.sadri.foursquare.utils.live_data.SingleLiveEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Coordinator @Inject constructor() {
    private val _navigationCommands: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()
    val navigationCommands: LiveData<NavigationCommand> = _navigationCommands

    fun navigate(directions: NavDirections) {
        _navigationCommands.postValue(NavigationCommand.To(directions))
    }

    fun navigate(command: NavigationCommand) {
        _navigationCommands.postValue(command)
    }
}