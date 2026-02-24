package com.example.company_profile.domain.model.shop

data class Shop(
    val id: String,
    val companyProfileId: String,
    val name: String,
    val description: String,
    val location: String,
    val imageUrl: String
)