package com.example.profile.domain.usecase

import com.example.profile.domain.model.UserProfile
import com.example.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(userId: String): Result<UserProfile> {
        return repository.getProfile(userId)
    }
}