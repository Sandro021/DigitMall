package com.example.company_profile.presentation.mapper

import com.example.company_profile.domain.model.reels.Reel
import com.example.company_profile.domain.model.shop.Shop
import com.example.company_profile.domain.model.shop.ShopItem
import com.example.company_profile.presentation.model.ReelUiModel
import com.example.company_profile.presentation.model.ShopItemUiModel
import com.example.company_profile.presentation.model.ShopUiModel

fun Reel.toUiModel() = ReelUiModel(
    id = id,
    videoUrl = videoUrl,
    coverUrl = coverUrl,
    caption = caption,
    stats = "$likesCount Likes • $commentsCount Comments"
)

fun Shop.toUiModel() = ShopUiModel(
    id = id,
    name = name,
    description = description,
    location = location,
    imageUrl = imageUrl
)

fun ShopItem.toUiModel() = ShopItemUiModel(
    id = id,
    shopId = shopId,
    name = name,
    formattedPrice = price,
    imageUrl = imageUrl,
    category = category
)