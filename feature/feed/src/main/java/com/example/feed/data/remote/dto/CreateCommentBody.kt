package com.example.feed.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateCommentBody(
    val reelId: String,
    val authorProfileId: String,
    val text: String,
    val createdAt: Long
)