package com.vidstagramtest.network.helpers

import com.vidstagramtest.model.network.PostBodyRequest
import com.vidstagramtest.network.VidstagramApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class PostNetHelper @Inject constructor(private val api: VidstagramApi) {

    suspend fun addPost(
        postBodyRequest: PostBodyRequest
    ): Flow<Response<ResponseBody>> {
        return flow {
            val calcResult =
                api.addPost(postBodyRequest)
            emit(calcResult)
        }.flowOn(Dispatchers.IO)
    }

}
