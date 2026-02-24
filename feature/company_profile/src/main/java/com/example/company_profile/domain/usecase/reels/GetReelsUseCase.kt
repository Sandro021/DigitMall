package com.example.company_profile.domain.usecase.reels

import com.example.company_profile.domain.model.reels.Reel
import com.example.company_profile.domain.repository.reels.ReelsRepository
import javax.inject.Inject

class GetReelsUseCase @Inject constructor(
    private val repository: ReelsRepository
) {
    suspend operator fun invoke(authorProfileId: String): Result<List<Reel>> {
        return repository.getReelsByCompany(authorProfileId)
    }
}