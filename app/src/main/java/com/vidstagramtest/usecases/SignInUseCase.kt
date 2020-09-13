package com.vidstagramtest.usecases

import com.google.firebase.auth.AuthResult
import com.vidstagramtest.repository.users.UsersRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend fun signIn(email: String, password: String): AuthResult? {
        return usersRepository.signIn(email, password)
    }
}