package com.vidstagramtest.usecases

import com.google.firebase.auth.FirebaseUser
import com.vidstagramtest.repository.users.UsersRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend fun getCurrentUser(): FirebaseUser? {
        return usersRepository.getCurrentUser()
    }
}
