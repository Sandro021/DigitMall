package com.example.item_list.data.mapper

import com.example.item_list.data.dto.ItemDto
import com.example.item_list.domain.model.Item

fun Item.toDto() = ItemDto(
    id = id,
    shopId = shopId,
    name = name,
    category = category,
    price = price,
    image = image,
    sizes = sizes
)