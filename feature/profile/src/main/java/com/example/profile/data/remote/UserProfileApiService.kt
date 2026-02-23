package com.example.profile.data.remote

import com.example.auth.data.remote.dto.ProfileDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserProfileApiService {
    @GET("profiles")
    suspend fun getProfileByFirebaseUid(
        @Query("firebaseUid") firebaseUid: String
    ): List<ProfileDto>

    @GET("profiles/{id}")
    suspend fun getProfileById(
        @Path("id") mockApiId: String
    ): ProfileDto

    @PUT("profiles/{id}")
    suspend fun updateProfile(
        @Path("id") mockApiId: String,
        @Body profile: ProfileDto
    ): ProfileDto
}