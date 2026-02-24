package com.example.feed.domain.usecase

import com.example.feed.domain.model.Reel
import com.example.feed.domain.model.Resource
import com.example.feed.domain.repository.FeedRepository
import javax.inject.Inject

class ToggleLikeUseCase @Inject constructor(
    private val repo: FeedRepository
) {
    suspend operator fun invoke(
        reelId: String,
        currentIsLiked: Boolean,
        currentLikesCount: Int
    ): Resource<Reel> = try {
        val newIsLiked = !currentIsLiked
        val newLikes = (currentLikesCount + if (newIsLiked) 1 else -1).coerceAtLeast(0)

        val updated = repo.patchLike(reelId, newIsLiked, newLikes)
        Resource.Success(updated)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Like update failed")
    }
}