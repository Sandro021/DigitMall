package com.example.item_feed.presentation.mapper

import com.example.item_feed.domain.model.Item
import com.example.item_feed.presentation.model.AllItemUi

fun Item.toUiModel() = AllItemUi(
    id = id,
    name = name,
    displayPrice = price,
    category = category,
    imageUrl = imageUrl
)