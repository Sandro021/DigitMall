package com.example.feed.domain.usecase

import com.example.feed.domain.model.ReelWithAuthor
import com.example.feed.domain.model.Resource
import com.example.feed.domain.repository.FeedRepository
import javax.inject.Inject
import kotlin.collections.associateBy
import kotlin.collections.mapNotNull
import kotlin.let

class GetFeedUseCase @Inject constructor(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(): Resource<List<ReelWithAuthor>> {
        val reelsResult = repository.getReels()
        val profilesResult = repository.getProfiles()

        return when {
            reelsResult is Resource.Error -> reelsResult
            profilesResult is Resource.Error -> profilesResult
            reelsResult is Resource.Success && profilesResult is Resource.Success -> {
                val profilesMap = profilesResult.data.associateBy { it.id }
                val reelsWithAuthors = reelsResult.data.mapNotNull { reel ->
                    profilesMap[reel.authorProfileId]?.let { author ->
                        ReelWithAuthor(reel = reel, author = author)
                    }
                }
                Resource.Success(reelsWithAuthors)
            }
            else -> Resource.Error("Unknown error")
        }
    }
}