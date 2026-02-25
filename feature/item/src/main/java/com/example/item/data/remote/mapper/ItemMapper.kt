package com.example.item.data.remote.mapper

import com.example.item.data.remote.dto.ItemDto
import com.example.item.domain.model.Item

fun ItemDto.toDomain(): Item = Item(
    id = id,
    name = name,
    price = price.toDouble(),
    image = image,
    category = category,
    shopId = shopId,
    sizes = sizes
)