package com.example.item_list.domain.repository

import com.example.item_list.domain.model.Item

interface ItemRepository {
    suspend fun getItemsByShopId(shopId: String): Result<List<Item>>

}