package com.example.feed.domain.repository

import com.example.feed.domain.model.Comment
import com.example.feed.domain.model.Profile
import com.example.feed.domain.model.Reel
import com.example.feed.domain.model.Resource


interface FeedRepository {
    suspend fun getReels(): Resource<List<Reel>>
    suspend fun getProfiles(): Resource<List<Profile>>
    suspend fun getComments(reelId: String): Resource<List<Comment>>
    suspend fun toggleLike(reelId: String, isLiked: Boolean): Resource<Reel>
}