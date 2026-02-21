package com.example.item_feed.data.mapper

import com.example.item_feed.data.dto.ItemDto
import com.example.item_feed.domain.model.Item
import java.util.UUID

fun ItemDto.toDomain(): Item {
    return Item(
        id = this.id ?: UUID.randomUUID().toString(),
        shopId = this.shopId ?: "",
        name = this.name ?: "Unknown Item",
        price = this.price?:"",
        category = this.category ?: "Uncategorized",
        imageUrl = this.image ?: "",
        sizes = this.sizes ?: emptyList()
    )
}