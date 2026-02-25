package com.example.feed.domain.usecase

import com.example.feed.data.local.UserInteractionStore
import com.example.feed.domain.model.Reel
import javax.inject.Inject
import kotlin.collections.forEach

class TrackInteractionUseCase @Inject constructor(
    private val interactionStore: UserInteractionStore
) {
    suspend fun updateWatchedPercent(reelId: String, percent: Float) {
        interactionStore.updateWatchedPercent(reelId, percent)
    }

    suspend fun markCompletedWatch(reelId: String, reel: Reel) {
        interactionStore.markCompletedWatch(reelId)
        // Update hashtag scores when completing watch
        reel.hashtags.forEach { hashtag ->
            interactionStore.updateHashtagScore(hashtag, 5f)
        }
    }

    suspend fun markLiked(reelId: String, reel: Reel) {
        interactionStore.markLiked(reelId)
        // Update hashtag scores when liking
        reel.hashtags.forEach { hashtag ->
            interactionStore.updateHashtagScore(hashtag, 10f)
        }
    }

    suspend fun markUnliked(reelId: String) {
        interactionStore.markUnliked(reelId)
    }

    suspend fun markReturnedToPrevious(reelId: String, reel: Reel) {
        interactionStore.markReturnedToPrevious(reelId)
        // Update hashtag scores when returning (strong interest)
        reel.hashtags.forEach { hashtag ->
            interactionStore.updateHashtagScore(hashtag, 15f)
        }
    }
}