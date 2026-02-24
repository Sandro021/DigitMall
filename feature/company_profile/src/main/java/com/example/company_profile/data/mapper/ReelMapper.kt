package com.example.company_profile.data.mapper

import com.example.company_profile.data.dto.reels.ReelDto
import com.example.company_profile.domain.model.reels.Reel


fun ReelDto.toDomain(): Reel {
    return Reel(
        id = id ?: "",
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
}

fun Reel.toDto(): ReelDto {
    return ReelDto(
        id = id.ifEmpty { null },
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
}