package com.example.digitmall.navigation


import com.example.shop_feed.presentation.navigation.ShopListRoute
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.cart.presentation.navigation.CartRoute
import com.example.cart.presentation.navigation.cartNavGraph
import com.example.item_feed.presentation.navigation.AllItemsRoute
import com.example.item_feed.presentation.navigation.allItemsNavGraph
import com.example.item_list.presentation.navigation.ItemListRoute
import com.example.item_list.presentation.navigation.itemListNavGraph
import com.example.shop_feed.presentation.navigation.shopListNavGraph

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ShopListRoute
    ) {

        allItemsNavGraph { }

        shopListNavGraph(
            onNavigateToItems = { shopId ->
                navController.navigate(ItemListRoute(shopId))
            }
        )
        cartNavGraph(
            onBackClick = { navController.popBackStack() }
        )
        itemListNavGraph(
            onBackClick = {
                navController.popBackStack()

            }, onCartClick = { navController.navigate(CartRoute) }
        )
    }
}