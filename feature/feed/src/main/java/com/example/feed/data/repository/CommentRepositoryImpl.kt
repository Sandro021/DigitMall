package com.example.feed.data.repository

import com.example.feed.data.remote.dto.CreateCommentBody
import com.example.feed.data.remote.mapper.toDomain
import com.example.feed.data.remote.service.FeedApi
import com.example.feed.domain.model.Comment
import com.example.feed.domain.repository.CommentsRepository
import javax.inject.Inject


    class CommentsRepositoryImpl @Inject constructor(
        private val api: FeedApi
    ) : CommentsRepository {

        override suspend fun postComment(reelId: String, authorId: String, text: String): Comment {
            val dto = api.postComment(
                reelId = reelId,
                body = CreateCommentBody(
                    reelId = reelId,
                    authorProfileId = authorId,
                    text = text,
                    createdAt = System.currentTimeMillis()
                )
            )
            return dto.toDomain()
        }
    }
