package com.example.feed.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feed.presentation.FeedScreen
import kotlinx.serialization.Serializable


@Serializable
data object FeedRoute

fun NavGraphBuilder.feedNavGraph() {

    composable<FeedRoute> {
        FeedScreen()
    }
}