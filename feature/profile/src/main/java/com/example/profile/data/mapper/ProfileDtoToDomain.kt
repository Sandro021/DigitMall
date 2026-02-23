package com.example.profile.data.mapper

import com.example.auth.data.remote.dto.ProfileDto
import com.example.profile.domain.model.UserProfile

fun ProfileDto.toDomain(): UserProfile {
    return UserProfile(
        id = this.id,
        email = this.email ?: "",
        username = this.username ?: "",
        firstName = this.firstName ?: "",
        lastName = this.lastName ?: "",
        avatarUrl = this.avatarUrl
    )
}

fun UserProfile.toUpdatedDto(existingDto: ProfileDto): ProfileDto {
    return existingDto.copy(
        username = this.username,
        firstName = this.firstName,
        lastName = this.lastName,
        avatarUrl = this.avatarUrl
    )
}