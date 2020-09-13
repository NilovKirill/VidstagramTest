package com.vidstagramtest.ui.viewmodel

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.vidstagramtest.ui.LoadingState
import com.vidstagramtest.usecases.CreateNewPostUseCase
import com.vidstagramtest.usecases.GetCurrentUserUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class NewPostViewModel @Inject constructor(
    private val createNewPostUseCase: CreateNewPostUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : BaseViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        loadingState.value = LoadingState.Error(throwable)
    }

    private var fileUrl: Uri? = null

    fun addNewPost(title: String) {
        viewModelScope.launch(exceptionHandler) {
            try {
                loadingState.value = LoadingState.Loading
                val user: FirebaseUser? = getCurrentUserUseCase.getCurrentUser()
                user?.let {
                    createNewPostUseCase.createPost(
                        it.uid,
                        it.displayName,
                        title,
                        fileUrl,
                        Date().time
                    ).collect { isSuccess ->
                        if (isSuccess) {
                            loadingState.value = LoadingState.Done
                        }
                    }
                }
            } catch (e: Exception) {
                loadingState.value = LoadingState.Error(e)
            }
        }
    }

    fun saveFileUrl(newFileUrl: Uri) {
        fileUrl = newFileUrl
    }

    fun getFileName(uri: Uri, context: Context): String? {
        var result: String? = null
        if (uri.scheme.equals("content")) {
            val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.getPath()
            val cut = result?.lastIndexOf('/')
            if (cut != null && cut != -1) {
                result = result?.substring(cut + 1)
            }
        }
        return result
    }

}