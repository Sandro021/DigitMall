package com.example.profile.presentation.model

data class ProfileUiModel(
    val id: String = "",
    val email: String = "",
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val avatarUrl: String? = null
)
