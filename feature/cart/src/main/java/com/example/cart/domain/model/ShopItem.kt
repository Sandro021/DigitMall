package com.example.cart.domain.model

data class ShopItem(
    val id: String,
    val shopId: String,
    val name: String,
    val category: String,
    val price: String,
    val image: String,
    val sizes: List<String>
)
