package com.example.company_profile.data.remote.reels

import com.example.company_profile.data.dto.reels.ReelDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ReelsApiService {
    @GET("reels")
    suspend fun getReelsByAuthor(
        @Query("authorProfileId") authorProfileId: String
    ): List<ReelDto>

    @POST("reels")
    suspend fun createReel(
        @Body reel: ReelDto
    ): ReelDto
}