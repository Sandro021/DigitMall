package com.example.feed.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReelDto(
    val id: String,
    val authorProfileId: String,
    val videoUrl: String,
    val coverUrl: String,
    val caption: String,
    val hashtags: List<String>,
    val likesCount: Int,
    val commentsCount: Int,
    val isLiked: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
