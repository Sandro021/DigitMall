package com.example.profile.domain.usecase


import com.example.profile.domain.model.UserProfile
import com.example.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(
        currentProfile: UserProfile,
        newName: String,
        newLastName: String,
        newUsername: String,
        newAvatarUriString: String?
    ): Result<UserProfile> {

        return runCatching {
            var finalAvatarUrl = currentProfile.avatarUrl

            if (newAvatarUriString != null) {
                val newUrl = repository.uploadAvatar(newAvatarUriString).getOrThrow()
                finalAvatarUrl = newUrl
            }

            val updatedProfile = currentProfile.copy(
                firstName = newName,
                lastName = newLastName,
                username = newUsername,
                avatarUrl = finalAvatarUrl
            )

            repository.updateProfile(updatedProfile).getOrThrow()
        }
    }
}