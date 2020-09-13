package com.vidstagramtest.usecases

import com.vidstagramtest.repository.users.UsersRepository
import javax.inject.Inject

class SinOutUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend fun signOut() {
        return usersRepository.signOut()
    }
}