package com.sadri.foursquare.ui.utils.mvi

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.Observer
import com.sadri.foursquare.ui.navigation.NavigationCommand
import com.sadri.foursquare.ui.navigation.NavigationFragment
import com.sadri.foursquare.ui.navigation.NavigationViewModel
import com.sadri.foursquare.ui.utils.snackBar
import kotlinx.android.synthetic.main.fragment_venue_detail.*
import timber.log.Timber

abstract class BaseMviFragment<STATE : BaseViewState, INTENT : MviIntent, ViewModel : BaseMviViewModel<STATE, INTENT, *>> :
    NavigationFragment() {

    abstract val viewModel: ViewModel

    private val viewStateObserver = Observer<STATE> {
        Timber.d("$TAG observed viewState : $it")
        render(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Registering observers
        viewModel.viewStates().observe(viewLifecycleOwner, viewStateObserver)
    }

    @CallSuper
    open fun render(viewState: STATE) {
        renderLoading(viewState.base)
        renderCoordinate(viewState.base)
        renderError(viewState.base)
    }

    override fun getViewModel(): NavigationViewModel? {
        return null
    }

    private fun renderError(baseState: BaseState) {
        if (baseState.error is BaseState.ErrorState.String) {
            requireContext().snackBar(
                baseState.error.snackBarMessageString,
                container
            )
        }
    }

    private fun renderLoading(baseState: BaseState) {
        if (baseState.showLoading) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    private fun renderCoordinate(baseState: BaseState) {
        if (baseState.coordinate !is NavigationCommand.Nothing) {
            navigate(baseState.coordinate)
        }
    }
}