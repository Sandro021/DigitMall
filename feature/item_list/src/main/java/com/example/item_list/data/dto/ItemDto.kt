package com.example.item_list.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    val id: String = "",
    val shopId: String,
    val name: String,
    val category: String,
    val price: String,
    val image: String,
    val sizes: List<String> = emptyList()
)