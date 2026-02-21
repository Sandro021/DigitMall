package com.example.item_list.presentation.contract

import com.example.item_list.presentation.model.ItemUi
import com.example.item_list.presentation.model.ShopItemUi

data class ItemListState(
    val isLoading: Boolean = false,
    val items: List<ItemUi> = emptyList(),
    val displayedItems: List<ItemUi> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String = "All",
    val cartCount: Int = 0,
    val error: String? = null
)
