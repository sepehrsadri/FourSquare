package com.sadri.foursquare.ui.utils.mvi

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.Observer
import com.sadri.foursquare.ui.utils.BaseFragment
import com.sadri.foursquare.ui.utils.mvi.model.BaseViewState
import com.sadri.foursquare.ui.utils.mvi.model.MviIntent
import com.sadri.foursquare.ui.utils.renderDefaults
import timber.log.Timber

abstract class BaseMviFragment<STATE : BaseViewState, INTENT : MviIntent, ViewModel : BaseMviViewModel<STATE, INTENT, *>> :
    BaseFragment() {

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
        renderDefaults(viewState)
    }
}