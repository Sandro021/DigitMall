package com.example.feed.domain.repository

import com.example.feed.domain.model.Comment

interface CommentsRepository {
    suspend fun postComment(reelId: String, authorId: String, text: String): Comment
}