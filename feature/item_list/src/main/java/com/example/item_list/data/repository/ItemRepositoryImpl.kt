package com.example.item_list.data.repository

import com.example.item_list.data.mapper.toDomain
import com.example.item_list.data.remote.ItemApiService
import com.example.item_list.domain.model.Item
import com.example.item_list.domain.repository.ItemRepository
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val api: ItemApiService
) : ItemRepository {

    override suspend fun getItemsByShopId(shopId: String): Result<List<Item>> = runCatching {
        api.getItems(shopId).map { it.toDomain() }
    }


}