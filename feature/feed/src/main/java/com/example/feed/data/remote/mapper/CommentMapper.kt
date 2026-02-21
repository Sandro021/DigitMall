package com.example.feed.data.remote.mapper

import com.example.feed.data.remote.dto.CommentDto
import com.example.feed.domain.model.Comment

fun CommentDto.toDomain(): Comment = Comment(
    id = id,
    reelId = reelId,
    authorProfileId = authorProfileId,
    text = text,
    createdAt = createdAt
)
