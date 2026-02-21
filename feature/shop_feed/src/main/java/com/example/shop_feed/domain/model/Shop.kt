package com.example.shop_feed.domain.model

data class Shop(
    val id: String,
    val name: String,
    val image: String,
    val description: String,
    val location: String,
    val phone: String
)