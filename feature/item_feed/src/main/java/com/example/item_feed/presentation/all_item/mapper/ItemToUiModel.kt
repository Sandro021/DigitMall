package com.example.item_feed.presentation.all_item.mapper

import com.example.item_feed.domain.model.Item
import com.example.item_feed.presentation.all_item.model.AllItemUi

fun Item.toUiModel() = AllItemUi(
    id = id,
    name = name,
    displayPrice = price,
    category = category,
    imageUrl = imageUrl
)