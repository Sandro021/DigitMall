package com.example.item_list.presentation.model

data class ItemUi(
    val id: String,
    val name: String,
    val category: String,
    val displayPrice: String,
    val imageUrl: String,
    val sizes: List<String>,
)
