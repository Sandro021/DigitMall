package com.example.feed.presentation

import com.example.feed.domain.model.Comment
import com.example.feed.domain.model.ReelWithAuthor

data class FeedState(
    val reels: List<ReelWithAuthor> = emptyList(),
    val currentPage: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val comments: Map<String, List<Comment>> = emptyMap(),
    val showCommentsSheet: String? = null,
    val watchProgress: Map<String, Float> = emptyMap()
)

sealed class FeedEvent {
    data object LoadFeed : FeedEvent()
    data object Retry : FeedEvent()
    data class OnPageChanged(val page: Int) : FeedEvent()
    data class OnLikeClicked(val reelId: String, val currentIsLiked: Boolean) : FeedEvent()
    data class OnCommentClicked(val reelId: String) : FeedEvent()
    data class OnShareClicked(val reelId: String) : FeedEvent()
    data object OnCommentsSheetDismissed : FeedEvent()
    data class OnWatchProgressChanged(val reelId: String, val percent: Float) : FeedEvent()
    data class OnReelCompleted(val reelId: String) : FeedEvent()
    data class OnReturnedToPrevious(val reelId: String) : FeedEvent()
    data class OnPostComment(val reelId: String, val text: String) : FeedEvent()
}

sealed class FeedSideEffect {
    data class ShowSnackbar(val message: String) : FeedSideEffect()
    data class NavigateTo(val route: String) : FeedSideEffect()
}
