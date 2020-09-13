package com.vidstagramtest.ui

sealed class LoadingState {

    object Loading : LoadingState()
    object Done : LoadingState()
    class Error(val throwable: Throwable) : LoadingState()

    object VerificationStart : LoadingState()
    class VerificationDone(val code: String) : LoadingState()
    object VerificationError : LoadingState()

}