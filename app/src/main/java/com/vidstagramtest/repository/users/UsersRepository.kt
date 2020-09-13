package com.vidstagramtest.repository.users

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.vidstagramtest.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun signIn(name: String, phoneAuth: PhoneAuthCredential): AuthResult?

    suspend fun signOut()

    suspend fun getCurrentUser(): FirebaseUser?

    suspend fun getAllUsers(): Flow<List<UserModel>>
}