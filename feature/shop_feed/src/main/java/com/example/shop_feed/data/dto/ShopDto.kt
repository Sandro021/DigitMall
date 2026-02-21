package com.example.shop_feed.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ShopDto(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val description: String = "",
    val location: String = "",
    val phone: String = ""
)