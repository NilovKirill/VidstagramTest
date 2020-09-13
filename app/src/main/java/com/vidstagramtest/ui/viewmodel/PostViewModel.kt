package com.vidstagramtest.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vidstagramtest.model.PostModel
import com.vidstagramtest.ui.LoadingState
import com.vidstagramtest.usecases.GetAllPostsUseCase
import com.vidstagramtest.usecases.NewPostsListenerUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostViewModel @Inject constructor(
    private val newPostsListenerUseCase: NewPostsListenerUseCase,
    private val getAllPostsUseCase: GetAllPostsUseCase
) : BaseViewModel() {

    val postLiveData: MutableLiveData<List<PostModel>> = MutableLiveData()

    private val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        loadingState.value = LoadingState.Error(throwable)
    }

    fun onStart() {
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
    }

}