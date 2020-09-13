package com.vidstagramtest.repository.users

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.vidstagramtest.model.UserModel
import com.vidstagramtest.repository.users.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UsersRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : UsersRepository {

    override suspend fun createNewUser(email: String, password: String): AuthResult? {
        return firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .await()
    }

    override suspend fun signIn(email: String, password: String): AuthResult? {
        return firebaseAuth
            .signInWithEmailAndPassword(email, password)
            .await()
    }

    override suspend fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override suspend fun getAllUsers(): Flow<List<UserModel>> {
        TODO("Not yet implemented")
    }
}