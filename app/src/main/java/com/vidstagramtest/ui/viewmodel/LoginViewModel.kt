package com.vidstagramtest.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.vidstagramtest.ui.LoadingState
import com.vidstagramtest.usecases.GetCurrentUserUseCase
import com.vidstagramtest.usecases.SignInUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : BaseViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        loadingState.value = LoadingState.Error(throwable)
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch(exceptionHandler) {
            try {
                loadingState.value = LoadingState.Loading
                val authResult = signInUseCase.signIn(email, password)
                authResult?.let {
                    loadingState.value = LoadingState.Done
                }
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
}