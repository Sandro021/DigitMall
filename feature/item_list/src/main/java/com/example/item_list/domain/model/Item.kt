package com.example.item_list.domain.model

data class Item(
    val id: String,
    val shopId: String,
    val name: String,
    val category: String,
    val price: String,
    val image: String,
    val sizes: List<String>
)