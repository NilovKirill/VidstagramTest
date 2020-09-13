package com.vidstagramtest.injectors

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.vidstagramtest.network.helpers.PostNetHelper
import com.vidstagramtest.repository.posts.PostsRepository
import com.vidstagramtest.repository.posts.PostsRepositoryImpl
import com.vidstagramtest.repository.users.UsersRepository
import com.vidstagramtest.repository.users.UsersRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideUsersRepository(
        firebaseAuth: FirebaseAuth
    ): UsersRepository {
        return UsersRepositoryImpl(
            firebaseAuth
        )
    }

    @Singleton
    @Provides
    fun providePostRepository(
        fireStore: FirebaseFirestore,
        storageRef: StorageReference,
        postNetHelper: PostNetHelper
    ): PostsRepository {
        return PostsRepositoryImpl(
            fireStore,
            storageRef,
            postNetHelper
        )
    }

}