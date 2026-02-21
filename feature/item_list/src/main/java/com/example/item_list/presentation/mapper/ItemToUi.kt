package com.example.item_list.presentation.mapper

import com.example.item_list.domain.model.Item
import com.example.item_list.presentation.model.ItemUi

fun Item.toUiModel(): ItemUi {
    return ItemUi(
        id = this.id,
        name = this.name,
        category = this.category,
        displayPrice = this.price,
        imageUrl = this.image,
        sizes = this.sizes,
        )
}

fun List<Item>.toUiList(): List<ItemUi> {
    return this.map { it.toUiModel() }
}