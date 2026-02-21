package com.example.shop_feed.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.shop_feed.presentation.ShopListScreen
import kotlinx.serialization.Serializable

@Serializable
data object ShopListRoute

fun NavGraphBuilder.shopListNavGraph(
    onNavigateToItems: (String) -> Unit
) {
    composable<ShopListRoute> {
        ShopListScreen(
            onShopClick = { shopId -> onNavigateToItems(shopId) }
        )
    }
}