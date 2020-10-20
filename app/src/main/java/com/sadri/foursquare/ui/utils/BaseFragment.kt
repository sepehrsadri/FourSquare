package com.sadri.foursquare.ui.utils

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sadri.foursquare.ui.navigation.NavigationCommand
import com.sadri.foursquare.utils.isFalseOrNull
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
abstract class BaseFragment : DaggerFragment() {
    companion object {
        private const val KEYBOARD_SHOW_DELAY = 100L
    }

    private var loadingDialog: FullScreenLoadingDialog? = null

    protected fun showKeyboard() {
        view?.findFocus()?.let {
            KeyboardUtils.showSoftKeyboard(it)
        }
    }

    protected fun hideKeyboard() {
        view?.findFocus()?.let {
            KeyboardUtils.hideSoftKeyboard(it)
        }
    }

    fun handleLoadingState(loading: Boolean?) {
        if (loading.isFalseOrNull()) {
            hideLoading()
        } else {
            showLoading()
        }
    }

    protected fun hideLoading() {
        loadingDialog?.apply { hide() }
    }

    protected fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = FullScreenLoadingDialog.getNewInstance(requireContext())
        }
        loadingDialog!!.show()
    }

    override fun onResume() {
        super.onResume()
        view?.postDelayed({ showKeyboard() }, KEYBOARD_SHOW_DELAY)
    }

    override fun onDestroyView() {
        if (loadingDialog != null) {
            loadingDialog!!.dismiss()
            loadingDialog = null
        }

        super.onDestroyView()
    }

    @Inject
    lateinit var coordinator: Coordinator

    @SuppressLint("RestrictedApi")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        coordinator.navigationCommands.observe(
            viewLifecycleOwner,
            Observer {
                navigate(it)
            }
        )
    }

    protected fun navigate(
        it: NavigationCommand
    ) {
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
            else -> {
                // Nothing
            }
        }
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

    abstract fun container(): View
}