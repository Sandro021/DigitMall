package com.example.item_feed.domain.model

data class Item(
    val id: String,
    val shopId: String,
    val name: String,
    val price: String,
    val category: String,
    val imageUrl: String,
    val sizes: List<String>
)

enum class SortOrder {
    NONE,
    PRICE_LOW_TO_HIGH,
    PRICE_HIGH_TO_LOW
}
