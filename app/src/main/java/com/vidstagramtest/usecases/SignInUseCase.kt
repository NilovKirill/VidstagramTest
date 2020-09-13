package com.vidstagramtest.usecases

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.vidstagramtest.repository.users.UsersRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend fun signIn(name: String, phoneAuth: PhoneAuthCredential): AuthResult? {
        return usersRepository.signIn(name, phoneAuth)
    }
}