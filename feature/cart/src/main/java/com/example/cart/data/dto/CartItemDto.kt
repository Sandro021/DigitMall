package com.example.cart.data.dto

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
data class CartItemDto(
    val id: String = "",
    val userId: String = "",
    val itemId: String = "",
    val name: String = "",
    val price: String = "",
    val image: String = "",
    val size: String = "",
    @OptIn(ExperimentalSerializationApi::class)
    @EncodeDefault
    val quantity: Int = 1
)