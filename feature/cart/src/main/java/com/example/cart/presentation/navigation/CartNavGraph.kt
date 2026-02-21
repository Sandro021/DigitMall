package com.example.cart.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.cart.presentation.CartScreen
import kotlinx.serialization.Serializable


@Serializable
data object CartRoute

fun NavGraphBuilder.cartNavGraph(
    onBackClick: () -> Unit
) {
    composable<CartRoute> {
        CartScreen(
            onBackClick = onBackClick
        )
    }
}