package com.example.item_feed.domain.repository

import com.example.item_feed.domain.model.Item

interface AllItemsRepository {
    suspend fun getAllItems(): List<Item>

    suspend fun addToCart(userId: String, item: Item, size: String, quantity: Int)

}