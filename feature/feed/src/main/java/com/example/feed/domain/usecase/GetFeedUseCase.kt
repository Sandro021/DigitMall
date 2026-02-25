package com.example.feed.domain.usecase

import com.example.feed.domain.model.AccountType
import com.example.feed.domain.model.Profile
import com.example.feed.domain.model.ReelWithAuthor
import com.example.feed.domain.model.Resource
import com.example.feed.domain.repository.FeedRepository
import javax.inject.Inject
import kotlin.collections.associateBy


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


                val reelsWithAuthors = reelsResult.data.map { reel ->


                    val author = profilesMap[reel.authorProfileId]
                        ?: createFallbackProfile(reel.authorProfileId)

                    ReelWithAuthor(reel = reel, author = author)
                }
                val sortedReels = reelsWithAuthors.sortedByDescending { it.reel.createdAt }

                Resource.Success(sortedReels)

            }

            else -> Resource.Error("Unknown error")
        }


    }

    private fun createFallbackProfile(id: String): Profile {
        return Profile(
            id = id,
            avatarUrl = "https://firebasestorage.googleapis.com/v0/b/digitapp-d0085.firebasestorage.app/o/avatars%2Fimages.png?alt=media&token=90b5915a-1b97-4725-b2d9-6f7c02acb41a",
            accountType = AccountType.BUSINESS,
            companyName = "",
            username = "new company",
            firstName = "",
            lastName = "",
            description = "",
            phone = "",
            location = "",
        )
    }
}