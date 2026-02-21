package com.example.item_feed.presentation.contract

import com.example.item_feed.domain.model.SortOrder
import com.example.item_feed.presentation.model.AllItemUi

data class AllItemsState(
    val isLoading: Boolean = false,
    val items: List<AllItemUi> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String = "All",
    val sortOrder: SortOrder = SortOrder.NONE,
    val error: String? = null,
    val cartMessage: String? = null
)
