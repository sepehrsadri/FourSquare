package com.sadri.foursquare.ui.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sadri.foursquare.ui.utils.BaseFragment

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
abstract class NavigationFragment : BaseFragment() {
    @SuppressLint("RestrictedApi")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getViewModel()?.navigationCommands?.observe(
            viewLifecycleOwner,
            Observer {
                val navController = findNavController()
                when (it) {
                    is NavigationCommand.To -> navController.navigate(
                        it.directions
                    )
                    is NavigationCommand.Back -> {
                        if (navController.popBackStack().not()) {
                            // We are on rootView. So we try to pass action to parent Activity
                            requireActivity().finish()
                        }
                    }
                    is NavigationCommand.BackTo -> navController.popBackStack(
                        it.destinationId,
                        false
                    )
                    is NavigationCommand.ToRoot -> navController.popBackStack(
                        navController.backStack.first.destination.id,
                        true
                    )
                }
            }
        )
    }

    fun <T> getNavigationResultLiveData(sharedName: String): MutableLiveData<T> {
        return findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<T>(sharedName)
            ?: error("SharedLiveData does not exists")
    }

    fun <T> removeNavigationResultLiveData(sharedName: String) {
        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.remove<T>(sharedName)
    }

    fun <T> setNavigationResultData(sharedName: String, value: T?) {
        findNavController().previousBackStackEntry
            ?.savedStateHandle
            ?.set(sharedName, value)
    }

    abstract override fun getViewModel(): NavigationViewModel?
}