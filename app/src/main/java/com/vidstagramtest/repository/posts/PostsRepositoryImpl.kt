package com.vidstagramtest.repository.posts

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference
import com.vidstagramtest.model.PostModel
import com.vidstagramtest.model.mappers.PostMapper
import com.vidstagramtest.model.network.PostBodyRequest
import com.vidstagramtest.network.helpers.PostNetHelper
import com.vidstagramtest.utils.FIRESTORE_COLLECTION_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject


class PostsRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val storageReference: StorageReference,
    private val postNetHelper: PostNetHelper
) : PostsRepository {

    private val mapper = PostMapper()

    override suspend fun createNewPost(
        userId: String,
        userName: String?,
        title: String,
        fileUri: Uri?,
        timestamp: Long
    ): Flow<Boolean> {
        var downloadUrl: String = ""
        fileUri?.let {
            val riversRef: StorageReference =
                storageReference.child(userId + "/videos/" + UUID.randomUUID().toString());
            riversRef.putFile(it).await()
            riversRef.downloadUrl.await().let { url ->
                downloadUrl = url.toString()
            }
        }

        val postBody = PostBodyRequest()
        postBody.userId = userId
        postBody.userName = userName ?: ""
        postBody.title = title
        postBody.videoUrl = downloadUrl
        postBody.timestamp = timestamp

        return flow {
            postNetHelper.addPost(postBody)
                .map {
                    return@map it.isSuccessful
                }.collect {
                    emit(it)
                }
        }
    }

    override suspend fun getPostsChanges(): List<PostModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPosts(): List<PostModel> {
        val snapshot: QuerySnapshot = fireStore.collection(FIRESTORE_COLLECTION_NAME)
            .get()
            .await()
        return mapper.mapToPostModel(snapshot.documents)
    }

}