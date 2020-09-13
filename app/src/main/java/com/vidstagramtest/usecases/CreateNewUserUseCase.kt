package com.vidstagramtest.usecases

import com.google.firebase.auth.AuthResult
import com.vidstagramtest.repository.users.UsersRepository
import javax.inject.Inject

class CreateNewUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend fun createUser(email: String, password: String): AuthResult? {
        return usersRepository.createNewUser(email, password)
    }
}