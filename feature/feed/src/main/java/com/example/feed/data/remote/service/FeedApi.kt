package com.example.feed.data.remote.service

import com.example.feed.data.remote.dto.CommentDto
import com.example.feed.data.remote.dto.CreateCommentBody
import com.example.feed.data.remote.dto.LikePatchBody
import com.example.feed.data.remote.dto.ProfileDto
import com.example.feed.data.remote.dto.ReelDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedApi {
    @GET("reels")
    suspend fun getReels(): List<ReelDto>

    @GET("profiles")
    suspend fun getProfiles(): List<ProfileDto>

    @GET("reels/{reelId}/comments")
    suspend fun getComments(@Path("reelId") reelId: String): List<CommentDto>

    @PATCH("reels/{id}")
    suspend fun patchLike(
        @Path("id") id: String,
        @Body body: LikePatchBody
    ): ReelDto

    @POST("reels/{reelId}/comments")
    suspend fun postComment(
        @Path("reelId") reelId: String,
        @Body body: CreateCommentBody
    ): CommentDto
}