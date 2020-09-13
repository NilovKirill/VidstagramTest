package com.vidstagramtest.repository.posts

import android.net.Uri
import com.vidstagramtest.model.PostModel
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    suspend fun createNewPost(
        userId: String,
        userName: String?,
        title: String,
        fileUri: Uri?,
        timestamp: Long
    ): Flow<Boolean>

    suspend fun getPostsChanges(): Flow<List<PostModel>>

    suspend fun getAllPosts(): List<PostModel>
}