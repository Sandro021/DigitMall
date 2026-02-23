package com.example.profile.presentation.contract

sealed class ProfileIntent {
    object LoadProfile : ProfileIntent()
    data class UpdateProfile(
        val firstName: String,
        val lastName: String,
        val username: String,
        val newAvatarUriString: String?
    ) : ProfileIntent()
}