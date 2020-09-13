package com.vidstagramtest.ui

sealed class LoadingState {

    object Loading : LoadingState()
    object Done : LoadingState()
    class Error(val throwable: Throwable) : LoadingState()

}