package com.example.shop_feed.data.mapper

import com.example.shop_feed.data.dto.ShopDto
import com.example.shop_feed.domain.model.Shop

fun Shop.toDto() = ShopDto(
    id = id,
    name = name,
    image = image,
    description = description,
    location = location,
    phone = phone
)