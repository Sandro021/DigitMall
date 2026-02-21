package com.example.feed.domain.usecase

import com.example.feed.domain.model.Reel
import com.example.feed.domain.model.Resource
import com.example.feed.domain.repository.FeedRepository
import javax.inject.Inject

class ToggleLikeUseCase @Inject constructor(
    private val repository: FeedRepository,
    private val trackInteractionUseCase: TrackInteractionUseCase
) {
    suspend operator fun invoke(reelId: String, currentIsLiked: Boolean, reel: Reel): Resource<Reel> {
        val result = repository.toggleLike(reelId, currentIsLiked)
        if (result is Resource.Success) {
            if (!currentIsLiked) {
                trackInteractionUseCase.markLiked(reelId, reel)
            } else {
                trackInteractionUseCase.markUnliked(reelId)
            }
        }
        return result
    }
}