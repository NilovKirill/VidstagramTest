package com.vidstagramtest.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.vidstagramtest.ui.LoadingState
import com.vidstagramtest.usecases.CreateNewUserUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewUserViewModel @Inject constructor(
    private val createNewUserUseCase: CreateNewUserUseCase
) : BaseViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        loadingState.value = LoadingState.Error(throwable)
    }

    fun onCreateButtonClick(email: String, password: String) {
        viewModelScope.launch(exceptionHandler) {
            try {
                loadingState.value = LoadingState.Loading
                val authResult = createNewUserUseCase.createUser(email, password)
                loadingState.value = LoadingState.Done
            } catch (e: Exception) {
                loadingState.value = LoadingState.Error(e)
            }
        }
    }
}