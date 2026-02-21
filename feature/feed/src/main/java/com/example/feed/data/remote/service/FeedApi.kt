package com.example.feed.data.remote.service

import com.example.feed.data.remote.dto.CommentDto
import com.example.feed.data.remote.dto.ProfileDto
import com.example.feed.data.remote.dto.ReelDto
import retrofit2.http.GET
import retrofit2.http.Path

interface FeedApi {
    @GET("reels")
    suspend fun getReels(): List<ReelDto>

    @GET("profiles")
    suspend fun getProfiles(): List<ProfileDto>

    @GET("reels/{reelId}/comments")
    suspend fun getComments(@Path("reelId") reelId: String): List<CommentDto>


}