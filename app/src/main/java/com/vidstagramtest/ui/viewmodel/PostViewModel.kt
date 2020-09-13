package com.vidstagramtest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vidstagramtest.model.PostModel
import com.vidstagramtest.ui.LoadingState
import com.vidstagramtest.usecases.GetAllPostsUseCase
import com.vidstagramtest.usecases.SinOutUseCase
import com.vidstagramtest.usecases.SubscribeToChangesUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostViewModel @Inject constructor(
    private val subscribeToChangesUseCase: SubscribeToChangesUseCase,
    private val getAllPostsUseCase: GetAllPostsUseCase,
    private val signOutUseCase: SinOutUseCase
) : BaseViewModel() {

    val postLiveData: MutableLiveData<List<PostModel>> = MutableLiveData()
    val changedPostsLiveData: MutableLiveData<List<PostModel>> = MutableLiveData()

    private val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        loadingState.value = LoadingState.Error(throwable)
    }

    fun onCreate() {
        viewModelScope.launch(exceptionHandler) {
            try {
                loadingState.value = LoadingState.Loading
                val posts: List<PostModel> = getAllPostsUseCase.getPosts()
                postLiveData.postValue(posts)
                loadingState.value = LoadingState.Done
            } catch (e: Exception) {
                loadingState.value = LoadingState.Error(e)
            }
        }
        viewModelScope.launch {
            try {
                subscribeToChangesUseCase.getNewPostListener().collect {
                    changedPostsLiveData.postValue(it)
                }
            } catch (e: Exception) {

            }
        }
    }

    fun signOut() {
        GlobalScope.launch(exceptionHandler) {
            signOutUseCase.signOut()
        }
    }
}