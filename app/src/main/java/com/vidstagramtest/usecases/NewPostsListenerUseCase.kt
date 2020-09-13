package com.vidstagramtest.usecases

import com.vidstagramtest.model.PostModel
import com.vidstagramtest.repository.posts.PostsRepository
import javax.inject.Inject

class NewPostsListenerUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {
    suspend fun getPosts(): List<PostModel> {
        return postsRepository.getPostsChanges()
    }
}