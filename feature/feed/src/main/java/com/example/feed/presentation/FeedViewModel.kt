package com.example.feed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feed.domain.model.Resource
import com.example.feed.domain.usecase.GetCommentsUseCase
import com.example.feed.domain.usecase.GetFeedUseCase
import com.example.feed.domain.usecase.PostCommentUseCase
import com.example.feed.domain.usecase.RankReelsUseCase
import com.example.feed.domain.usecase.ToggleLikeUseCase
import com.example.feed.domain.usecase.TrackInteractionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.find
import kotlin.collections.getOrNull

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getFeedUseCase: GetFeedUseCase,
    private val rankReelsUseCase: RankReelsUseCase,
    private val toggleLikeUseCase: ToggleLikeUseCase,
    private val trackInteractionUseCase: TrackInteractionUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val postCommentUseCase: PostCommentUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FeedState())
    val state: StateFlow<FeedState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<FeedSideEffect>()
    val sideEffect: SharedFlow<FeedSideEffect> = _sideEffect.asSharedFlow()

    init {
        handleEvent(FeedEvent.LoadFeed)
    }

    fun handleEvent(event: FeedEvent) {
        when (event) {
            is FeedEvent.LoadFeed -> loadFeed()
            is FeedEvent.Retry -> loadFeed()
            is FeedEvent.OnPageChanged -> onPageChanged(event.page)
            is FeedEvent.OnLikeClicked -> onLikeClicked(event.reelId, event.currentIsLiked)
            is FeedEvent.OnCommentClicked -> onCommentClicked(event.reelId)
            is FeedEvent.OnShareClicked -> onShareClicked(event.reelId)
            is FeedEvent.OnCommentsSheetDismissed -> onCommentsSheetDismissed()
            is FeedEvent.OnWatchProgressChanged -> onWatchProgressChanged(event.reelId, event.percent)
            is FeedEvent.OnReelCompleted -> onReelCompleted(event.reelId)
            is FeedEvent.OnReturnedToPrevious -> onReturnedToPrevious(event.reelId)
            is FeedEvent.OnPostComment -> postComment(event.reelId, event.text)
        }
    }

    private fun postComment(reelId: String, text: String) {
        viewModelScope.launch {
            val trimmed = text.trim()
            if (trimmed.isEmpty()) return@launch

            val currentUserId = "user1"

            when (val result = postCommentUseCase(reelId, currentUserId, trimmed)) {
                is Resource.Success -> {
                    _state.update { state ->
                        val current = state.comments[reelId].orEmpty()
                        state.copy(comments = state.comments + (reelId to (current + result.data)))
                    }
                }
                is Resource.Error -> {
                    _sideEffect.emit(FeedSideEffect.ShowSnackbar("Failed to post comment: ${result.message}"))
                }
                else -> Unit
            }
        }
    }

    private fun loadFeed() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when (val result = getFeedUseCase()) {
                is Resource.Success -> {
                    val rankedReels = rankReelsUseCase(result.data)
                    _state.update {
                        it.copy(
                            reels = rankedReels,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun onPageChanged(page: Int) {
        _state.update { it.copy(currentPage = page) }

        // Track when user returns to previous page
        val previousPage = _state.value.currentPage
        if (page < previousPage && previousPage > 0) {
            val previousReelId = _state.value.reels.getOrNull(previousPage)?.reel?.id
            if (previousReelId != null) {
                handleEvent(FeedEvent.OnReturnedToPrevious(previousReelId))
            }
        }
    }

    private fun onLikeClicked(reelId: String, currentIsLiked: Boolean) {
        viewModelScope.launch {
            val reelWithAuthor = _state.value.reels.find { it.reel.id == reelId } ?: return@launch
            val reel = reelWithAuthor.reel

            val newIsLiked = !currentIsLiked
            val newLikesCount = (reel.likesCount + if (newIsLiked) 1 else -1).coerceAtLeast(0)

            // 1) optimistic UI update
            _state.update { state ->
                state.copy(
                    reels = state.reels.map {
                        if (it.reel.id == reelId) it.copy(reel = it.reel.copy(isLiked = newIsLiked, likesCount = newLikesCount))
                        else it
                    }
                )
            }

            val result = toggleLikeUseCase(reelId, newIsLiked, newLikesCount) // change your usecase signature
            if (result is Resource.Error) {
                // rollback on failure
                _sideEffect.emit(FeedSideEffect.ShowSnackbar("Failed to update like: ${result.message}"))
                _state.update { state ->
                    state.copy(
                        reels = state.reels.map {
                            if (it.reel.id == reelId) it.copy(reel = it.reel.copy(isLiked = currentIsLiked, likesCount = reel.likesCount))
                            else it
                        }
                    )
                }
            }
        }
    }

    private fun onCommentClicked(reelId: String) {
        viewModelScope.launch {
            _state.update { it.copy(showCommentsSheet = reelId) }

            // Load comments if not already loaded
            if (!_state.value.comments.containsKey(reelId)) {
                when (val result = getCommentsUseCase(reelId)) {
                    is Resource.Success -> {
                        _state.update { state ->
                            state.copy(comments = state.comments + (reelId to result.data))
                        }
                    }
                    is Resource.Error -> {
                        _sideEffect.emit(FeedSideEffect.ShowSnackbar("Failed to load comments: ${result.message}"))
                        _state.update { state ->
                            state.copy(comments = state.comments + (reelId to emptyList()))
                        }
                    }
                    is Resource.Loading -> {
                        // Could show loading state
                    }
                }
            }
        }
    }

    private fun onShareClicked(reelId: String) {
        viewModelScope.launch {
            _sideEffect.emit(FeedSideEffect.ShowSnackbar("Share feature coming soon!"))
        }
    }

    private fun onCommentsSheetDismissed() {
        _state.update { it.copy(showCommentsSheet = null) }
    }

    private fun onWatchProgressChanged(reelId: String, percent: Float) {
        viewModelScope.launch {
            trackInteractionUseCase.updateWatchedPercent(reelId, percent)
            _state.update { state ->
                state.copy(
                    watchProgress = state.watchProgress + (reelId to percent)
                )
            }

            // Mark as completed if >= 90%
            if (percent >= 0.9f) {
                val reel = _state.value.reels.find { it.reel.id == reelId }?.reel
                if (reel != null) {
                    trackInteractionUseCase.markCompletedWatch(reelId, reel)
                }
            }
        }
    }

    private fun onReelCompleted(reelId: String) {
        viewModelScope.launch {
            val reel = _state.value.reels.find { it.reel.id == reelId }?.reel
                ?: return@launch
            trackInteractionUseCase.markCompletedWatch(reelId, reel)
        }
    }

    private fun onReturnedToPrevious(reelId: String) {
        viewModelScope.launch {
            val reel = _state.value.reels.find { it.reel.id == reelId }?.reel
                ?: return@launch
            trackInteractionUseCase.markReturnedToPrevious(reelId, reel)
        }
    }


}