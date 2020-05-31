package com.sadri.foursquare.ui.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
abstract class BaseViewModel : ViewModel() {
    private val observingLiveDataList = HashSet<LiveDataAndObserver<*>>()

    val fullscreenLoading = MutableLiveData(false)

    fun <T : Any?> observe(liveData: LiveData<T>, observer: Observer<T>) {
        observingLiveDataList.add(
            LiveDataAndObserver(
                liveData,
                observer
            )
        )
    }

    override fun onCleared() {
        for (liveDataAndObserver in observingLiveDataList) {
            liveDataAndObserver.stopObserving()
        }

        super.onCleared()
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