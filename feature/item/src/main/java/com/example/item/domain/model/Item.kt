package com.example.item.domain.model

data class Item(
    val id: String,
    val name: String,
    val image: String,
    val category: String,
    val price: Double,
    val sizes: List<String>,
    val shopId: String
)
