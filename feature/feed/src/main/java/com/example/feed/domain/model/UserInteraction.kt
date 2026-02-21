package com.example.feed.domain.model

data class UserInteraction(
    val reelId: String,
    val watchedPercent: Float = 0f, // 0..1
    val completedWatch: Boolean = false,
    val liked: Boolean = false,
    val returnedToPrevious: Boolean = false,
    val lastWatchedAt: Long = System.currentTimeMillis()
)
