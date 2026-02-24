package com.example.feed.data.repository

import com.example.feed.data.remote.dto.LikePatchBody
import com.example.feed.data.remote.mapper.toDomain
import com.example.feed.data.remote.service.FeedApi
import com.example.feed.domain.model.Comment
import com.example.feed.domain.model.Profile
import com.example.feed.domain.model.Reel
import com.example.feed.domain.model.Resource
import com.example.feed.domain.repository.FeedRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val api: FeedApi
) : FeedRepository {

    override suspend fun getReels(): Resource<List<Reel>> {
        return try {
            delay(500) // Simulate network delay
            val reels = api.getReels().map { it.toDomain() }
            Resource.Success(reels)
        } catch (e: Exception) {
            Resource.Error("Failed to fetch reels: ${e.message}", e)
        }
    }

    override suspend fun getProfiles(): Resource<List<Profile>> {
        return try {
            val profiles = api.getProfiles().map { it.toDomain() }
            Resource.Success(profiles)
        } catch (e: Exception) {
            Resource.Error("Failed to fetch profiles: ${e.message}", e)
        }
    }

    override suspend fun getComments(reelId: String): Resource<List<Comment>> {
        return try {
            val comments = api.getComments(reelId).map { it.toDomain() }
            Resource.Success(comments)
        } catch (e: Exception) {
            Resource.Error("Failed to fetch comments: ${e.message}", e)
        }
    }

    override suspend fun toggleLike(reelId: String, isLiked: Boolean): Resource<Reel> {
        return try {
            // In a real app, this would call an API endpoint
            // For now, we'll simulate it by fetching reels and updating the one
            val reelsResult = getReels()
            if (reelsResult is Resource.Success) {
                val reel = reelsResult.data.find { it.id == reelId }
                    ?: return Resource.Error("Reel not found")
                val updatedReel = reel.copy(
                    isLiked = !isLiked,
                    likesCount = if (isLiked) reel.likesCount - 1 else reel.likesCount + 1
                )
                Resource.Success(updatedReel)
            } else {
                Resource.Error("Failed to update like")
            }
        } catch (e: Exception) {
            Resource.Error("Failed to toggle like: ${e.message}", e)
        }
    }

    override suspend fun patchLike(reelId: String, isLiked: Boolean, likesCount: Int): Reel {
        val dto = api.patchLike(reelId, LikePatchBody(isLiked, likesCount))
        return dto.toDomain()
    }
}
