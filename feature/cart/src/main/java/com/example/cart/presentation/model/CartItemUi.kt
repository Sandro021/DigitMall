package com.example.cart.presentation.model

data class CartItemUi(
    val id: String,
    val itemId: String,
    val name: String,
    val imageUrl: String,
    val size: String,
    val quantity: Int,
    val singlePriceDisplay: String,
    val totalPriceDisplay: String
)
