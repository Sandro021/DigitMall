package com.example.auth.data.remote.service

import com.example.auth.data.remote.dto.ProfileDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileApi {
    @POST("profiles")
    suspend fun createProfile(@Body dto: ProfileDto): ProfileDto

    @GET("profiles")
    suspend fun getProfilesByFirebaseUid(
        @Query("firebaseUid") firebaseUid: String
    ): List<ProfileDto>

    // optional: GET /profiles/{id}
    @GET("profiles/{id}")
    suspend fun getProfileById(
        @Path("id") id: String
    ): ProfileDto

}