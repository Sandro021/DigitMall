package com.example.feed.domain.model

data class Comment(
    val id: String,
    val reelId: String,
    val authorProfileId: String,
    val text: String,
    val createdAt: Long
)
