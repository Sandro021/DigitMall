package com.example.feed.data.remote.mapper

import com.example.feed.data.remote.dto.ReelDto
import com.example.feed.domain.model.Reel


fun ReelDto.toDomain(): Reel = Reel(
    id = id,
    authorProfileId = authorProfileId,
    videoUrl = videoUrl,
    coverUrl = coverUrl,
    caption = caption,
    hashtags = hashtags,
    likesCount = likesCount,
    commentsCount = commentsCount,
    isLiked = isLiked,
    createdAt = createdAt
)

fun Reel.toDto(): ReelDto = ReelDto(
    id = id,
    authorProfileId = authorProfileId,
    videoUrl = videoUrl,
    coverUrl = coverUrl,
    caption = caption,
    hashtags = hashtags,
    likesCount = likesCount,
    commentsCount = commentsCount,
    isLiked = isLiked,
    createdAt = createdAt
)
