package com.vidstagramtest.injectors

import com.vidstagramtest.network.RetrofitManager
import com.vidstagramtest.network.VidstagramApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
object NetworkModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRestoreApi(retrofit: Retrofit): VidstagramApi {
        return retrofit.create(VidstagramApi::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitManger(): RetrofitManager {
        return RetrofitManager()
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(retrofitManager: RetrofitManager): Retrofit {
        return retrofitManager.getVidstagramRetrofit()
    }
}