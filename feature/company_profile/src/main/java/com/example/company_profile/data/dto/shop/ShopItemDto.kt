package com.example.company_profile.data.dto.shop

import kotlinx.serialization.Serializable

@Serializable
data class ShopItemDto(
    val id: String? = null,
    val shopId: String = "",
    val name: String = "",
    val price: String = "",
    val image: String = "",
    val category: String = "",
    val sizes: List<String> = emptyList()
)
