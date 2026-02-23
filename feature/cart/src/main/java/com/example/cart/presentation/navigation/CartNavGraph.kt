package com.example.cart.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.cart.presentation.CartScreen
import kotlinx.serialization.Serializable


@Serializable
data class CartRoute(val totalAmount: Float)

fun NavGraphBuilder.cartNavGraph(
    onBackClick: () -> Unit,
    onCheckOutClicked: (Float) -> Unit
) {
    composable<CartRoute> { backStackEntry ->
        val isPaymentSuccessful = backStackEntry.savedStateHandle
            .get<Boolean>("payment_successful") ?: false

        CartScreen(
            onBackClick = onBackClick,
            onCheckOutClicked = onCheckOutClicked,
            isPaymentSuccessful = isPaymentSuccessful,
            onClearCartComplete = {
                backStackEntry.savedStateHandle["payment_successful"] = false
            }
        )
    }
}