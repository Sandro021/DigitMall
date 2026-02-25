package com.example.item.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CartDto(
    val userId: String,
    val itemId: String,
    val price: String,
    val image: String,
    val size: String,
    val quantity: Int,
    val name: String
)