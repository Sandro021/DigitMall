package com.example.company_profile.domain.model.reels

data class Reel(
    val id: String,
    val authorProfileId: String,
    val videoUrl: String,
    val coverUrl: String,
    val caption: String,
    val hashtags: List<String> = emptyList(),
    val likesCount: Int,
    val commentsCount: Int,
    val isLiked: Boolean,
    val createdAt: Long
)

