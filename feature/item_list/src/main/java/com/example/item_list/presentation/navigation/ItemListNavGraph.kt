package com.example.item_list.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.item_list.presentation.ItemListScreen
import kotlinx.serialization.Serializable

@Serializable
data class ItemListRoute(val shopId: String)

fun NavGraphBuilder.itemListNavGraph(
    onBackClick: () -> Unit,
    onCartClick: () -> Unit
) {
    composable<ItemListRoute> { backStackEntry ->
        val route: ItemListRoute = backStackEntry.toRoute()

        ItemListScreen(shopId = route.shopId, onBackClick = onBackClick, onCartClick = onCartClick)
    }
}