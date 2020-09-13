package com.vidstagramtest.ui

import com.google.firebase.auth.PhoneAuthCredential

sealed class LoadingState {

    object Loading : LoadingState()
    object Done : LoadingState()
    class Error(val throwable: Throwable) : LoadingState()

    object VerificationStart : LoadingState()
    object VerificationGetCode : LoadingState()
    class VerificationDone(val phoneCred: PhoneAuthCredential) : LoadingState()
    object VerificationError : LoadingState()

}