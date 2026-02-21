package com.example.feed.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui.R
import com.example.ui.theme.MallTheme
import com.example.ui.theme.Padding
import com.example.ui.theme.VerticalSpacing
import com.example.ui.theme.common.LogoLoader
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel()
) {
    MallTheme(darkTheme = true) {
        val state by viewModel.state.collectAsState()
        val snackbarHostState = remember { SnackbarHostState() }

        // Handle side effects
        LaunchedEffect(Unit) {
            viewModel.sideEffect.collectLatest { effect ->
                when (effect) {
                    is FeedSideEffect.ShowSnackbar -> {
                        snackbarHostState.showSnackbar(effect.message)
                    }
                    is FeedSideEffect.NavigateTo -> {
                        // Handle navigation if needed
                    }
                }
            }
        }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                when {
                    state.isLoading && state.reels.isEmpty() -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            LogoLoader(
                                size = 92.dp,
                                logoRes = R.drawable.logo
                            )
                        }
                    }

                    state.error != null && state.reels.isEmpty() -> {
                        // Error state
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(Padding.padding16),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = state.error ?: "Unknown error",
                                color = Color.White,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(VerticalSpacing.s16))
                            Button(onClick = { viewModel.handleEvent(FeedEvent.Retry) }) {
                                Text("Retry")
                            }
                        }
                    }

                    state.reels.isNotEmpty() -> {
                        val pagerState = rememberPagerState(
                            initialPage = 0,
                            pageCount = { state.reels.size }
                        )

                        LaunchedEffect(pagerState.currentPage) {
                            viewModel.handleEvent(FeedEvent.OnPageChanged(pagerState.currentPage))
                        }

                        VerticalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxSize()
                        ) { page ->
                            val reelWithAuthor = state.reels[page]
                            val isPlaying = pagerState.currentPage == page

                            ReelItem(
                                reelWithAuthor = reelWithAuthor,
                                isPlaying = isPlaying,
                                onLikeClick = {
                                    viewModel.handleEvent(
                                        FeedEvent.OnLikeClicked(
                                            reelWithAuthor.reel.id,
                                            reelWithAuthor.reel.isLiked
                                        )
                                    )
                                },
                                onCommentClick = {
                                    viewModel.handleEvent(
                                        FeedEvent.OnCommentClicked(reelWithAuthor.reel.id)
                                    )
                                },
                                onShareClick = {
                                    viewModel.handleEvent(
                                        FeedEvent.OnShareClicked(reelWithAuthor.reel.id)
                                    )
                                },
                                onProgressUpdate = { progress ->
                                    viewModel.handleEvent(
                                        FeedEvent.OnWatchProgressChanged(
                                            reelWithAuthor.reel.id,
                                            progress
                                        )
                                    )
                                }
                            )
                        }
                    }
                }

                // Comments bottom sheet
                state.showCommentsSheet?.let { reelId ->
                    val comments = state.comments[reelId] ?: emptyList()
                    CommentsBottomSheet(
                        reelId = reelId,
                        comments = comments,
                        onDismiss = {
                            viewModel.handleEvent(FeedEvent.OnCommentsSheetDismissed)
                        }
                    )
                }
            }
        }
    }
}