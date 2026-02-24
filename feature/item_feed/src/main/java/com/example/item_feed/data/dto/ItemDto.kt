package com.example.item_feed.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    val id: String? = null,
    val shopId: String? = null,
    val name: String? = null,
    val price: String? = null,
    val category: String? = null,
    val image: String? = null,
    val sizes: List<String>? = null,
)