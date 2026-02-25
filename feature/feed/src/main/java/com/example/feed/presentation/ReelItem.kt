package com.example.feed.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.feed.domain.model.ReelWithAuthor
import com.example.ui.theme.AppColors
import com.example.ui.theme.Padding
import com.example.ui.theme.VerticalSpacing

@Composable
fun ReelItem(
    reelWithAuthor: ReelWithAuthor,
    isPlaying: Boolean,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    onProgressUpdate: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val reel = reelWithAuthor.reel
    val author = reelWithAuthor.author

    Box(modifier = modifier.fillMaxSize()) {
        if (reel.videoUrl.isNotEmpty() && reel.videoUrl.startsWith("http")) {
            VideoPlayer(
                videoUrl = reel.videoUrl,
                isPlaying = isPlaying,
                onProgressUpdate = onProgressUpdate,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Placeholder image
            AsyncImage(
                model = reel.coverUrl.ifEmpty { "https://via.placeholder.com/400x700" },
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            AppColors.OverlayGradientEnd
                        )
                    )
                )
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(Padding.padding16)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(VerticalSpacing.s8)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Padding.padding8)
                ) {
                    AsyncImage(
                        model = author.avatarUrl?.ifEmpty { "https://via.placeholder.com/50" },
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "@${author.username}",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Text(
                    text = reel.caption,
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 3
                )

                if (reel.hashtags.isNotEmpty()) {
                    Text(
                        text = reel.hashtags.joinToString(" ") { "#$it" },
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Column(
                modifier = Modifier.padding(start = Padding.padding16),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(VerticalSpacing.s16)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(VerticalSpacing.s4)
                ) {
                    Icon(
                        imageVector = if (reel.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (reel.isLiked) AppColors.LikeRed else Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { onLikeClick() }
                    )
                    Text(
                        text = formatCount(reel.likesCount),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(VerticalSpacing.s4)
                ) {
                    Icon(
                        imageVector = Icons.Default.Comment,
                        contentDescription = "Comment",
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { onCommentClick() }
                    )
                    Text(
                        text = formatCount(reel.commentsCount),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }

                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onShareClick() }
                )
            }
        }
    }
}


private fun formatCount(count: Int): String {
    return when {
        count >= 1_000_000 -> "${(count / 1_000_000f).let { if (it % 1 == 0f) it.toInt() else String.format("%.1f", it) }}M"
        count >= 1_000 -> "${(count / 1_000f).let { if (it % 1 == 0f) it.toInt() else String.format("%.1f", it) }}K"
        else -> count.toString()
    }
}
