package com.example.item.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    val id: String,
    val name: String,
    val price: String,
    val image: String,
    val category: String,
    val shopId: String,
    val sizes: List<String> = emptyList()
)

