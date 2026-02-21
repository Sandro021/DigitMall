package com.example.cart.data.mapper

import com.example.cart.data.dto.CartItemDto
import com.example.cart.domain.model.CartItem

fun CartItemDto.toDomain() = CartItem(
    id = id,
    userId = userId,
    itemId = itemId,
    name = name,
    price = price,
    image = image,
    size = size,
    quantity = quantity
)

fun CartItem.toDto() = CartItemDto(
    id = id,
    userId = userId,
    itemId = itemId,
    name = name,
    price = price,
    image = image,
    size = size,
    quantity = quantity
)