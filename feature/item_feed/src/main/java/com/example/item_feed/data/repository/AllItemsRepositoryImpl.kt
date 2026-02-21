package com.example.item_feed.data.repository


import com.example.item_feed.data.dto.CartItemRequestDto
import com.example.item_feed.data.mapper.toDomain
import com.example.item_feed.data.remote.AllItemApiService
import com.example.item_feed.domain.model.Item
import com.example.item_feed.domain.repository.AllItemsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class AllItemsRepositoryImpl @Inject constructor(
    private val api: AllItemApiService
) : AllItemsRepository {

    override suspend fun getAllItems(): List<Item> = coroutineScope {
        val shops = api.getShops()


        val itemsDeferred = shops.map { shop ->
            async {

                val shopId = shop.id
                if (!shopId.isNullOrBlank()) {
                    try {
                        val items = api.getItemsForShop(shopId)
                        items
                    } catch (e: Exception) {
                        emptyList()
                    }
                } else {
                    emptyList()
                }
            }
        }

        val allItems = itemsDeferred.awaitAll().flatten()
        return@coroutineScope allItems.map { it.toDomain() }
    }

    override suspend fun addToCart(
        userId: String,
        item: Item,
        size: String,
        quantity: Int
    ) {
        val request = CartItemRequestDto(
            userId = userId,
            itemId = item.id,
            price = item.price,
            image = item.imageUrl,
            size = size,
            quantity = quantity,
            name = item.name
        )

        api.addToCart(request)
    }
}