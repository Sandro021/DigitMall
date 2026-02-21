package com.example.shop_feed.presentation.contract

import com.example.shop_feed.presentation.model.ShopUiModel

data class ShopListState(
    val isLoading: Boolean = false,
    val shops: List<ShopUiModel> = emptyList(),
    val error: String? = null
)