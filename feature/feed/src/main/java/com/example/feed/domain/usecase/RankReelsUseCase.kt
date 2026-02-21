package com.example.feed.domain.usecase

import com.example.feed.data.local.UserInteractionStore
import com.example.feed.domain.model.Reel
import com.example.feed.domain.model.ReelWithAuthor
import com.example.feed.domain.model.UserInteraction
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.collections.forEach
import kotlin.collections.last
import kotlin.collections.map
import kotlin.collections.set
import kotlin.collections.sortedByDescending
import kotlin.ranges.coerceAtMost
import kotlin.to

class RankReelsUseCase @Inject constructor(
    private val interactionStore: UserInteractionStore
) {
    suspend operator fun invoke(reels: List<ReelWithAuthor>): List<ReelWithAuthor> {
        if (reels.isEmpty()) return reels

        val interactions = interactionStore.getAllInteractions().first()
        val hashtagScores = interactionStore.getHashtagInterestScores().first()

        val scoredReels = reels.map { reelWithAuthor ->
            val reel = reelWithAuthor.reel
            val interaction = interactions[reel.id]
            val score = calculateScore(reel, interaction, hashtagScores, reelWithAuthor.author.id)
            reelWithAuthor to score
        }.sortedByDescending { it.second }

        // Apply diversity: don't show same author back-to-back more than 2 times
        return applyDiversity(scoredReels.map { it.first })
    }

    private fun calculateScore(
        reel: Reel,
        interaction: UserInteraction?,
        hashtagScores: Map<String, Float>,
        authorId: String
    ): Float {
        var score = 100f // Base score

        // Hashtag interest scores
        reel.hashtags.forEach { hashtag ->
            score += hashtagScores[hashtag] ?: 0f
        }

        // Like boost
        if (interaction?.liked == true) {
            score += 50f
        }

        // Completion boost
        if (interaction?.completedWatch == true) {
            score += 30f
        }

        // Returned to previous boost (strong interest signal)
        if (interaction?.returnedToPrevious == true) {
            score += 40f
        }

        // Recency boost (more recent = higher score)
        val daysSinceCreation = (System.currentTimeMillis() - reel.createdAt) / (1000 * 60 * 60 * 24)
        score += (30f / (1 + daysSinceCreation)).coerceAtMost(30f)

        return score
    }

    private fun applyDiversity(reels: List<ReelWithAuthor>): List<ReelWithAuthor> {
        if (reels.size <= 3) return reels

        val result = mutableListOf<ReelWithAuthor>()
        val authorCounts = mutableMapOf<String, Int>()

        for (reel in reels) {
            val authorId = reel.author.id
            val count = authorCounts[authorId] ?: 0

            // Allow same author up to 2 times in a row, then skip
            if (count < 2 || result.isEmpty() || result.last().author.id != authorId) {
                result.add(reel)
                if (result.size > 1 && result[result.size - 2].author.id == authorId) {
                    authorCounts[authorId] = count + 1
                } else {
                    authorCounts[authorId] = 1
                }
            }
        }

        return result
    }
}