package com.vidstagramtest.repository.users

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.vidstagramtest.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun createNewUser(email: String, password: String): AuthResult?

    suspend fun signIn(email: String, password: String): AuthResult?

    suspend fun getCurrentUser(): FirebaseUser?

    suspend fun getAllUsers(): Flow<List<UserModel>>
}