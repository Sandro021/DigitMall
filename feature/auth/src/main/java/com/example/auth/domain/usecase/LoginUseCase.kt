package com.example.auth.domain.usecase

import com.example.auth.domain.model.Profile
import com.example.auth.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Profile {
        return repo.login(email, password)
    }
}