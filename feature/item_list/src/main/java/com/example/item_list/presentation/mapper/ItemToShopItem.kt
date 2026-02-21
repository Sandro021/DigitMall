package com.example.item_list.presentation.mapper

import com.example.item_list.presentation.model.ItemUi
import com.example.item_list.presentation.model.ShopItemUi

fun ItemUi.toShopItem(): ShopItemUi {
    return ShopItemUi(
        id = this.id,
        name = this.name,
        category = this.category,
        displayPrice = this.displayPrice,
        imageUrl = this.imageUrl,
        sizes = this.sizes,

        )

}