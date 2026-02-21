package com.example.item_list.presentation.mapper

import com.example.cart.domain.model.ShopItem
import com.example.item_list.presentation.model.ShopItemUi

fun ShopItemUi.toDomain(): ShopItem {
    return ShopItem(
        id = this.id,
        shopId = "unknown",
        name = this.name,
        category = this.category,
        price = this.displayPrice,
        image = this.imageUrl,
        sizes = this.sizes
    )
}