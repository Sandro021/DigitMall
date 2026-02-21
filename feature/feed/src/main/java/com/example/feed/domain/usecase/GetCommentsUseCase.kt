package com.example.feed.domain.usecase

import com.example.feed.domain.model.Comment
import com.example.feed.domain.model.Resource
import com.example.feed.domain.repository.FeedRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(reelId: String): Resource<List<Comment>> {
        return repository.getComments(reelId)
    }
}