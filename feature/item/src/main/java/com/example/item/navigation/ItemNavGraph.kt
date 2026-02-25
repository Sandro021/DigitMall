package com.example.item.navigation

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.item.presentation.ItemScreen
import com.example.item.presentation.ItemContract
import com.example.item.presentation.ItemViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.serialization.Serializable

@Serializable
data object ItemRoute

fun NavGraphBuilder.itemNavGraph(
    onBack: () -> Unit
) {
    composable(
        route = "item_screen/{shopId}/{itemId}",
        arguments = listOf(
            navArgument("shopId") { type = NavType.StringType },
            navArgument("itemId") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val shopId = backStackEntry.arguments?.getString("shopId") ?: ""
        val itemId = backStackEntry.arguments?.getString("itemId") ?: ""

        val viewModel: ItemViewModel = hiltViewModel()
        val state = viewModel.state.collectAsState().value

        // Only call Load once per navigation
        androidx.compose.runtime.LaunchedEffect(shopId, itemId) {
            viewModel.onEvent(ItemContract.Event.Load(shopId, itemId))
        }

        ItemScreen(
            state = state,
            onEvent = viewModel::onEvent,
            onBack = onBack
        )
    }
}