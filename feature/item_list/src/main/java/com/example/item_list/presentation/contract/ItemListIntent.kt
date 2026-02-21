package com.example.item_list.presentation.contract

import com.example.item_list.presentation.model.ItemUi


sealed class ItemListIntent {

    data class LoadItems(val shopId: String) : ItemListIntent()
    data class SelectCategory(val category: String) : ItemListIntent()
    data class AddToCart(val item: ItemUi, val selectedSize: String) : ItemListIntent()

}
