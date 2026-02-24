package com.example.company_profile.data.dto.shop

import kotlinx.serialization.Serializable

@Serializable
data class ShopDto(
    val id: String? = null,
    val companyProfileId: String,
    val name: String,
    val description: String,
    val location: String,
    val image: String
)
