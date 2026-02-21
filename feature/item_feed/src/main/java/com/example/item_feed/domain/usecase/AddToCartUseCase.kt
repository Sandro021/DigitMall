package com.example.item_feed.domain.usecase

import com.example.item_feed.domain.model.Item
import com.example.item_feed.domain.repository.AllItemsRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repository: AllItemsRepository
) {
    suspend operator fun invoke(item: Item, userId: String) {

        val defaultSize = item.sizes.firstOrNull() ?: "30"

        repository.addToCart(userId = userId, item = item, size = defaultSize, quantity = 1)
    }
}