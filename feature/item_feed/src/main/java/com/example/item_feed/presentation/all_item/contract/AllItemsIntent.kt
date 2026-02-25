package com.example.item_feed.presentation.all_item.contract

import com.example.item_feed.domain.model.SortOrder
import com.example.item_feed.presentation.all_item.model.AllItemUi

sealed class AllItemsIntent {
    data object LoadItems : AllItemsIntent()
    data class SelectCategory(val category: String) : AllItemsIntent()
    data class ChangeSortOrder(val sortOrder: SortOrder) : AllItemsIntent()

    data class AddToCart(val item: AllItemUi) : AllItemsIntent()

    data object ClearCartMessage : AllItemsIntent()
    data class SelectShop(val shopId: String) : AllItemsIntent()
}