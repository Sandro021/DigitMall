package com.example.payment.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.payment.presentation.CheckoutScreen
import kotlinx.serialization.Serializable

@Serializable
data class CheckOutRoute(val totalAmount: Float)


fun NavGraphBuilder.checkoutNavGraph(
    onBackClick: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    composable<CheckOutRoute> { backStackEntry ->
        val args = backStackEntry.toRoute<CheckOutRoute>()

        CheckoutScreen(
            totalAmount = args.totalAmount,
            onBackClick,
            onPaymentSuccess = onPaymentSuccess

        )
    }
}