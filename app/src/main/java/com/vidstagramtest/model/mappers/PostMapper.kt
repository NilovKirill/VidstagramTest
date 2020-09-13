package com.vidstagramtest.model.mappers

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.vidstagramtest.model.PostModel


class PostMapper {

    fun mapToPostModel(listDocuments: List<DocumentSnapshot>): List<PostModel> {
        var resultList = mutableListOf<PostModel>()
        listDocuments.forEach { doc ->
            if (doc.exists()) {
                val post: PostModel? = doc.toObject(PostModel::class.java)
                post?.let {
                    resultList.add(it)
                }
            }
        }
        return resultList
    }
}