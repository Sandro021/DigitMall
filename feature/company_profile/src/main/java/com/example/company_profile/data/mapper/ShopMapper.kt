package com.example.company_profile.data.mapper

import com.example.company_profile.data.dto.shop.ShopDto
import com.example.company_profile.data.dto.shop.ShopItemDto
import com.example.company_profile.domain.model.shop.Shop
import com.example.company_profile.domain.model.shop.ShopItem


fun ShopDto.toDomain() = Shop(
    id = id ?: "",
    companyProfileId = companyProfileId,
    name = name,
    description = description,
    location = location,
    imageUrl = image
)

fun Shop.toDto() = ShopDto(
    id = id.ifEmpty { null },
    companyProfileId = companyProfileId,
    name = name,
    description = description,
    location = location,
    image = imageUrl
)

fun ShopItemDto.toDomain() = ShopItem(
    id = id ?: "",
    shopId = shopId,
    name = name,
    price = price,
    imageUrl = image,
    category = category,
    sizes = sizes
)

fun ShopItem.toDto() = ShopItemDto(
    id = id.ifEmpty { null },
    shopId = shopId,
    name = name,
    price = price,
    image = imageUrl,
    category = category,
    sizes = sizes
)