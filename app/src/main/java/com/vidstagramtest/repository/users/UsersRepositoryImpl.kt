package com.vidstagramtest.repository.users

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.vidstagramtest.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UsersRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : UsersRepository {

    override suspend fun signIn(name: String, phoneAuth: PhoneAuthCredential): AuthResult? {
        return firebaseAuth
            .signInWithCredential(phoneAuth)
            .await()
    }

    override suspend fun signOut() {
        return firebaseAuth.signOut()
    }

    override suspend fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override suspend fun getAllUsers(): Flow<List<UserModel>> {
        TODO("Not yet implemented")
    }
}