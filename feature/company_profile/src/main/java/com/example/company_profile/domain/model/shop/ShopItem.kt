package com.example.company_profile.domain.model.shop

data class ShopItem(
    val id: String,
    val shopId: String,
    val name: String,
    val price: String,
    val imageUrl: String,
    val category: String,
    val sizes: List<String>
)
