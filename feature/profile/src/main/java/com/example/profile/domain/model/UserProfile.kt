package com.example.profile.domain.model

data class UserProfile(
    val id: String,
    val email: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String?
)