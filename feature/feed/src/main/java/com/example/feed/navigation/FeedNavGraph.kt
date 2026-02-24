package com.example.feed.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feed.presentation.FeedScreen
import com.example.ui.theme.LocalMallColors
import com.example.ui.theme.MallTheme
import kotlinx.serialization.Serializable
import com.example.ui.theme.DarkMallColors


@Serializable
data object FeedRoute

fun NavGraphBuilder.feedNavGraph() {

    composable<FeedRoute> {

        MallTheme(darkTheme = true) {
            CompositionLocalProvider(
                LocalMallColors provides DarkMallColors
            ) {
                FeedScreen()
            }
        }
    }
}