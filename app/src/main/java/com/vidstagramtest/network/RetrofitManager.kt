package com.vidstagramtest.network

import com.google.gson.GsonBuilder
import com.vidstagramtest.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject

class RetrofitManager @Inject constructor() {

    fun getVidstagramRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(UnsafeOkHttpClient().getUnsafeOkHttpClient().build())
            .build()
    }
}