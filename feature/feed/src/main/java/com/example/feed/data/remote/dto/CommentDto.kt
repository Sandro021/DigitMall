package com.example.feed.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
    val id: String,
    val reelId: String,
    val authorProfileId: String,
    val text: String,
    val createdAt: Long
)