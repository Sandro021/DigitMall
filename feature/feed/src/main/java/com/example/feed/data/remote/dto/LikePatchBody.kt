package com.example.feed.data.remote.dto

data class LikePatchBody(
    val isLiked: Boolean,
    val likesCount: Int
)