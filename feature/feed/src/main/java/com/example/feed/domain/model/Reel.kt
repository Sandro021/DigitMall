package com.example.feed.domain.model

data class Reel(
    val id: String,
    val authorProfileId: String,
    val videoUrl: String,
    val coverUrl: String,
    val caption: String,
    val hashtags: List<String>,
    val likesCount: Int,
    val commentsCount: Int,
    val isLiked: Boolean,
    val createdAt: Long = System.currentTimeMillis()
)