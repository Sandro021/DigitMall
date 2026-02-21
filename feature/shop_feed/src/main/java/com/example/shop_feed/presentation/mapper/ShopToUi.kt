package com.example.shop_feed.presentation.mapper

import com.example.shop_feed.domain.model.Shop
import com.example.shop_feed.presentation.model.ShopUiModel

fun Shop.toUiModel(): ShopUiModel {
    return ShopUiModel(
        id = this.id,
        name = this.name,
        imageUrl = this.image,
        description = this.description,
        location = this.location,
        phone = this.phone
    )
}

fun List<Shop>.toUiModelList(): List<ShopUiModel> {
    return this.map { it.toUiModel() }
}