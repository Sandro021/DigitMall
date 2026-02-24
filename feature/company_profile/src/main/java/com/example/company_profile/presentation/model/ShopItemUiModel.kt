package com.example.company_profile.presentation.model

data class ShopItemUiModel(
    val id: String,
    val shopId: String,
    val name: String,
    val formattedPrice: String,
    val imageUrl: String,
    val category : String
)