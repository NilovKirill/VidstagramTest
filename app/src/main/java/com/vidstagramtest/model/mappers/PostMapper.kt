package com.vidstagramtest.model.mappers

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.vidstagramtest.model.PostModel


class PostMapper {

    fun mapDocToPostModel(listDocuments: List<DocumentSnapshot>): List<PostModel> {
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

    fun mapDocChangeToPostModel(listDocuments: List<DocumentChange>): List<PostModel> {
        var resultList = mutableListOf<PostModel>()
        listDocuments.forEach { docChange ->
            val doc: DocumentSnapshot = docChange.document
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