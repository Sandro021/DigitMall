package com.example.auth.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    @SerialName("id") val id: String,
    @SerialName("firebaseUid") val firebaseUid: String?,
    @SerialName("email") val email: String?,
    @SerialName("accountType") val accountType: String,

    @SerialName("username") val username: String?,
    @SerialName("avatarUrl") val avatarUrl: String?,

    @SerialName("firstName") val firstName: String?,
    @SerialName("lastName") val lastName: String?,

    @SerialName("phone") val phone: String?,
    @SerialName("companyName") val companyName: String?,
    @SerialName("location") val location: String?,
    @SerialName("description") val description: String?
)