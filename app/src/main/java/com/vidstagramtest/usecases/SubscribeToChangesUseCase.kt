package com.vidstagramtest.usecases

import com.vidstagramtest.model.PostModel
import com.vidstagramtest.repository.posts.PostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscribeToChangesUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {
    suspend fun getNewPostListener(): Flow<List<PostModel>> {
        return postsRepository.getPostsChanges()
    }
}