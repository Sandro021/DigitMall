package com.example.profile.presentation.mapper

import com.example.profile.domain.model.UserProfile
import com.example.profile.presentation.model.ProfileUiModel

fun UserProfile.toUiModel(): ProfileUiModel {
    return ProfileUiModel(
        id = this.id,
        email = this.email,
        username = this.username,
        firstName = this.firstName,
        lastName = this.lastName,
        avatarUrl = this.avatarUrl
    )
}