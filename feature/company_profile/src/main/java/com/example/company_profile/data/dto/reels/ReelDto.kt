package com.example.company_profile.data.dto.reels

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
data class ReelDto @OptIn(
    ExperimentalSerializationApi::class,
    ExperimentalSerializationApi::class
) constructor(
    val id: String? = null,
    val authorProfileId: String,
    val videoUrl: String,
    val coverUrl: String,
    val caption: String,
    @EncodeDefault val hashtags: List<String> = emptyList(),
    @EncodeDefault val likesCount: Int = 0,
    @EncodeDefault val commentsCount: Int = 0,
    @EncodeDefault val isLiked: Boolean = false,
    @EncodeDefault val createdAt: Long = 0
)