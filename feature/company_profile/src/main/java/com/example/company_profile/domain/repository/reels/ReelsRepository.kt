package com.example.company_profile.domain.repository.reels

import com.example.company_profile.domain.model.reels.Reel

interface ReelsRepository {

    suspend fun uploadVideo(localUriString: String): Result<String>

    suspend fun createReel(reel: Reel): Result<Reel>

    suspend fun getReelsByCompany(authorProfileId: String): Result<List<Reel>>
}