package com.vidstagramtest.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.vidstagramtest.ui.LoadingState
import com.vidstagramtest.usecases.GetCurrentUserUseCase
import com.vidstagramtest.usecases.SignInUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : BaseViewModel() {

    private var verificationCodeId: String = ""

    private val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        loadingState.value = LoadingState.Error(throwable)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            loadingState.value = LoadingState.VerificationDone(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            loadingState.value = LoadingState.VerificationError
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            verificationCodeId = verificationId
            loadingState.value = LoadingState.VerificationGetCode
        }
    }

    fun signInWithPhoneAuthCredential(name: String, credential: PhoneAuthCredential) {
        viewModelScope.launch(exceptionHandler) {
            try {
                loadingState.value = LoadingState.Loading
                signInUseCase.signIn(name, credential)
                loadingState.value = LoadingState.Done
            } catch (e: Exception) {
                loadingState.value = LoadingState.Error(e)
            }
        }
    }


    fun onStart() {
        viewModelScope.launch(exceptionHandler) {
            try {
                val user = getCurrentUserUseCase.getCurrentUser()
                user?.let {
                    loadingState.value = LoadingState.Done
                }
            } catch (e: Exception) {

            }
        }
    }

    fun signIn(name: String, phoneCode: String) {
        viewModelScope.launch(exceptionHandler) {
            try {
                loadingState.value = LoadingState.Loading
                val credential = PhoneAuthProvider.getCredential(verificationCodeId, phoneCode)
                signInUseCase.signIn(name, credential)
                loadingState.value = LoadingState.Done
            } catch (e: Exception) {
                loadingState.value = LoadingState.Error(e)
            }
        }
    }

    fun verifyPhoneNumber(phoneNumber: String) {
        loadingState.value = LoadingState.VerificationStart
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            callbacks
        )
    }
}