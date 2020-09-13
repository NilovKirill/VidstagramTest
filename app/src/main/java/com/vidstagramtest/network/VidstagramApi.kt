package com.vidstagramtest.network

import com.vidstagramtest.model.network.PostBodyRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface VidstagramApi {

    @POST("/addPost")
    suspend fun addPost(
        @Body data: PostBodyRequest
    ): Response<ResponseBody>
}