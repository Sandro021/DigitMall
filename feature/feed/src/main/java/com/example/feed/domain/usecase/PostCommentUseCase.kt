package com.example.feed.domain.usecase

import com.example.feed.domain.model.Comment
import com.example.feed.domain.model.Resource
import com.example.feed.domain.repository.CommentsRepository
import javax.inject.Inject

class PostCommentUseCase @Inject constructor(
    private val repo: CommentsRepository
) {
    suspend operator fun invoke(reelId: String, authorId: String, text: String): Resource<Comment> =
        try {
            Resource.Success(repo.postComment(reelId, authorId, text))
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Post comment failed")
        }
}