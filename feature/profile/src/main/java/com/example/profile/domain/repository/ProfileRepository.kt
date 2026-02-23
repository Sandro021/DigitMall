package com.example.profile.domain.repository

import com.example.profile.domain.model.UserProfile

interface ProfileRepository {

    suspend fun getProfile(userId: String): Result<UserProfile>
    suspend fun updateProfile(profile: UserProfile): Result<UserProfile>
    suspend fun uploadAvatar(localUriString: String): Result<String>

}