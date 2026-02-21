package com.example.shop_feed.presentation.contract

sealed class ShopListIntent {
    object LoadShops : ShopListIntent()
    data class OnShopClicked(val shopId: String) : ShopListIntent()
}