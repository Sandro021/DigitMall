package com.example.item_feed.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.item_feed.presentation.all_item.AllItemsFeedScreen
import kotlinx.serialization.Serializable

@Serializable
data object AllItemsRoute

fun NavGraphBuilder.allItemsNavGraph(
    onItemClick: (shopId: String, itemId: String) -> Unit
) {
    composable<AllItemsRoute> {
        AllItemsFeedScreen(
            onItemClick = { shopId, itemId ->
                onItemClick(shopId, itemId)
            }
        )
    }
}