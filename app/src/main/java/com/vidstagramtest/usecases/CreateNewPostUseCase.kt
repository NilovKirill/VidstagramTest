package com.vidstagramtest.usecases

import android.net.Uri
import com.vidstagramtest.repository.posts.PostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CreateNewPostUseCase @Inject constructor(
    private val postRepository: PostsRepository
) {
    suspend fun createPost(
        userId: String,
        userName: String?,
        title: String,
        fileUri: Uri?,
        timestamp: Long
    ): Flow<Boolean> {
        return postRepository.createNewPost(userId, userName, title, fileUri, timestamp)
    }
}

