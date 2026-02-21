package com.example.item_feed.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CartItemRequestDto(
    val userId: String,
    val itemId: String,
    val price: String,
    val image: String,
    val size: String,
    val quantity: Int,
    val name: String
)
