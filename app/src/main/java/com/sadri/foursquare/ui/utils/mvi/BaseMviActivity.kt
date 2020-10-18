package com.sadri.foursquare.ui.utils.mvi

import android.os.Bundle
import androidx.lifecycle.Observer
import com.sadri.foursquare.ui.utils.BaseActivity
import com.sadri.foursquare.ui.utils.mvi.model.MviIntent
import com.sadri.foursquare.ui.utils.mvi.model.MviViewState
import timber.log.Timber

abstract class BaseMviActivity<STATE : MviViewState, INTENT : MviIntent, ViewModel : BaseMviViewModel<STATE, INTENT, *>> :
    BaseActivity() {

    abstract val viewModel: ViewModel

    private val viewStateObserver = Observer<STATE> {
        Timber.d("$TAG observed viewState : $it")
        render(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Registering observers
        viewModel.viewStates().observe(this, viewStateObserver)
    }

    abstract fun render(viewState: STATE)
}