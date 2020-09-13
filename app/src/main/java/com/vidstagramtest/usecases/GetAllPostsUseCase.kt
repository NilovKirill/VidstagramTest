package com.vidstagramtest.usecases

import com.vidstagramtest.model.PostModel
import com.vidstagramtest.repository.posts.PostsRepository
import javax.inject.Inject

class GetAllPostsUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {
    suspend fun getPosts(): List<PostModel> {
        return postsRepository.getAllPosts()
    }
}
