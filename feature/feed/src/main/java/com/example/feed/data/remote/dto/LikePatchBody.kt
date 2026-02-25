package com.example.feed.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LikePatchBody(
    val isLiked: Boolean,
    val likesCount: Int
)