package com.vidstagramtest.ui.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vidstagramtest.ui.LoadingState


abstract class BaseViewModel : ViewModel() {
    val loadingState: MutableLiveData<LoadingState> = MutableLiveData()
}