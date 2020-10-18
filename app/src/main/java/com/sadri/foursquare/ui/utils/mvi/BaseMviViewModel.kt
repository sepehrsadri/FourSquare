package com.sadri.foursquare.ui.utils.mvi

import androidx.annotation.CallSuper
import androidx.lifecycle.*
import com.sadri.foursquare.ui.utils.mvi.model.MviIntent
import com.sadri.foursquare.ui.utils.mvi.model.MviResult
import com.sadri.foursquare.ui.utils.mvi.model.MviViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseMviViewModel<STATE : MviViewState, INTENT : MviIntent, RESULT : MviResult>(
    initialState: STATE
) : ViewModel(),
    ViewModelContract<INTENT>,
    ReducerContract<STATE, RESULT> {
    private val observingLiveDataList = HashSet<LiveDataAndObserver<*>>()

    private val _viewStates: MutableLiveData<STATE> = MutableLiveData(initialState)
    fun viewStates(): LiveData<STATE> = _viewStates

    fun newResult(result: RESULT) {
        Timber.d("$TAG new result : $result")
        viewModelScope.launch(Dispatchers.Main) {
            val previousState = _viewStates.value!!
            val newState = reduce(previousState, result)
            _viewStates.value = newState
            Timber.d("$TAG : reducer reduced \n previous state $previousState \n to new state $newState")
        }
    }

    @CallSuper
    override fun dispatch(intent: INTENT) {
        if (!viewStates().hasObservers()) {
            throw NoObserverAttachedException("No observer attached. In case of custom View \"startObserving()\" function needs to be called manually.")
        }
        Timber.d("$TAG processing intent: $intent")
    }

    override fun onCleared() {
        for (liveDataAndObserver in observingLiveDataList)
            liveDataAndObserver.stopObserving()

        super.onCleared()

        Timber.d("$TAG onCleared")
    }

    fun <T : Any?> observe(liveData: LiveData<T>, observer: Observer<T>) {
        observingLiveDataList.add(
            LiveDataAndObserver(
                liveData,
                observer
            )
        )
    }

    fun <T : Any?> observeWithInitUpdate(liveData: LiveData<T>, observer: Observer<T>) {
        observingLiveDataList.add(
            LiveDataAndObserver(
                liveData,
                observer
            )
        )
        observer.onChanged(liveData.value)
    }


    private class LiveDataAndObserver<T>(
        val liveData: LiveData<T>,
        val observer: Observer<T>
    ) {
        init {
            liveData.observeForever(observer)
        }

        fun stopObserving() {
            liveData.removeObserver(observer)
        }
    }
}