package com.example.cart.domain.model

data class CartItem(
    val id: String = "",
    val userId: String,
    val itemId: String,
    val name: String,
    val price: String,
    val image: String,
    val size: String,
    val quantity: Int = 1
)