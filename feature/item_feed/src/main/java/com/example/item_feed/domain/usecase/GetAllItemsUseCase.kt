package com.example.item_feed.domain.usecase

import com.example.item_feed.domain.model.Item
import com.example.item_feed.domain.model.SortOrder
import com.example.item_feed.domain.repository.AllItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllItemsUseCase @Inject constructor(
    private val repository: AllItemsRepository
) {
    operator fun invoke(
        shopId: String? = null,               // <-- NEW
        categoryFilter: String? = null,
        sortOrder: SortOrder = SortOrder.NONE
    ): Flow<List<Item>> = flow {
        var processedList = repository.getAllItems()

        // Filter by shopId
        if (!shopId.isNullOrBlank()) {
            processedList = processedList.filter { it.shopId == shopId }
        }

        // Filter by category
        if (!categoryFilter.isNullOrBlank() && categoryFilter != "All") {
            processedList = processedList.filter { it.category == categoryFilter }
        }

        // Sort
        processedList = when (sortOrder) {
            SortOrder.PRICE_LOW_TO_HIGH -> processedList.sortedBy { it.price }
            SortOrder.PRICE_HIGH_TO_LOW -> processedList.sortedByDescending { it.price }
            SortOrder.NONE -> processedList
        }

        emit(processedList)
    }
}