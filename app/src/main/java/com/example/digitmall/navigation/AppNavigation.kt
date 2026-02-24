package com.example.digitmall.navigation


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Museum
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.auth.navigation.WelcomeRoute
import com.example.auth.navigation.authGraph
import com.example.cart.presentation.navigation.CartRoute
import com.example.cart.presentation.navigation.cartNavGraph
import com.example.company_profile.presentation.navigation.companyProfileNavGraph
import com.example.digitmall.dynamic_profile.navigation.DynamicProfileRoute
import com.example.digitmall.dynamic_profile.navigation.dynamicProfileNavGraph
import com.example.feed.navigation.FeedRoute
import com.example.feed.navigation.feedNavGraph
import com.example.item_feed.presentation.navigation.AllItemsRoute
import com.example.item_feed.presentation.navigation.allItemsNavGraph
import com.example.item_list.presentation.navigation.ItemListRoute
import com.example.item_list.presentation.navigation.itemListNavGraph
import com.example.payment.navigation.CheckOutRoute
import com.example.payment.navigation.checkoutNavGraph
import com.example.profile.presentation.navigation.profileNavGraph
import com.example.shop_feed.presentation.navigation.ShopListRoute
import com.example.shop_feed.presentation.navigation.shopListNavGraph
import com.example.ui.theme.MallTheme


data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: Any
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val bottomTabs = listOf(
        BottomNavItem("Shops", Icons.Default.Storefront, ShopListRoute),
        BottomNavItem("All Items", Icons.AutoMirrored.Filled.List, AllItemsRoute),
        BottomNavItem("Cart", Icons.Default.ShoppingCart, CartRoute(0.0F)),
        BottomNavItem("Feed", Icons.Default.VideoLibrary, FeedRoute),
        BottomNavItem("Profile", Icons.Default.Museum, DynamicProfileRoute)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = currentDestination?.hierarchy?.any { dest ->
        bottomTabs.any { tab -> dest.hasRoute(tab.route::class) }
    } == true

    Scaffold(
        containerColor = MallTheme.colors.background,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp))
                        .clip(RoundedCornerShape(24.dp)),
                    containerColor = MallTheme.colors.surface,
                    tonalElevation = 0.dp
                ) {
                    bottomTabs.forEach { tab ->
                        val isSelected = currentDestination.hierarchy.any {
                            it.hasRoute(tab.route::class)
                        }

                        NavigationBarItem(
                            selected = isSelected,
                            label = { Text(tab.label) },
                            icon = { Icon(tab.icon, contentDescription = tab.label) },

                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MallTheme.colors.brandSecondary,
                                selectedTextColor = MallTheme.colors.brandSecondary,
                                indicatorColor = MallTheme.colors.brandSecondary.copy(alpha = 0.15f),
                                unselectedIconColor = MallTheme.colors.textSecondary,
                                unselectedTextColor = MallTheme.colors.textSecondary
                            ),
                            onClick = {
                                navController.navigate(tab.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = WelcomeRoute,
            modifier = Modifier.padding(paddingValues)
        ) {
            authGraph(
                navController = navController,
                onAuthSuccess = {
                    navController.navigate(ShopListRoute) {
                        popUpTo(WelcomeRoute) { inclusive = true }
                    }
                }
            )
            dynamicProfileNavGraph(onBackClick = { navController.popBackStack() })

            companyProfileNavGraph(onBackClick = { navController.popBackStack() })

            profileNavGraph(
                onBackClick = { navController.popBackStack() }
            )
            allItemsNavGraph { }

            shopListNavGraph(
                onNavigateToItems = { shopId ->
                    navController.navigate(ItemListRoute(shopId))
                }
            )
            checkoutNavGraph(
                onBackClick = { navController.popBackStack() },
                onPaymentSuccess = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("payment_successful", true)

                    navController.popBackStack()
                }
            )

            cartNavGraph(
                onBackClick = { navController.popBackStack() },
                onCheckOutClicked = { totalAmountDouble ->

                    navController.navigate(CheckOutRoute(totalAmount = totalAmountDouble))
                }
            )

            itemListNavGraph(
                onBackClick = { navController.popBackStack() },
                onCartClick = {
                    navController.navigate(
                        CartRoute(
                            totalAmount = 0F
                        )
                    )
                }
            )

            feedNavGraph()
        }
    }
}