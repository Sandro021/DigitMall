package com.example.feed.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.feed.domain.model.Comment
import com.example.ui.theme.Padding
import com.example.ui.theme.VerticalSpacing


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsBottomSheet(
    reelId: String,
    comments: List<Comment>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var newCommentText by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
        ) {
            TopAppBar(
                title = { Text("Comments") }
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = Padding.padding16)
            ) {
                if (comments.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(VerticalSpacing.s32),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No comments yet",
                                fontSize = 14.sp
                            )
                        }
                    }
                } else {
                    items(comments) { comment ->
                        CommentItem(comment = comment)
                        Spacer(modifier = Modifier.height(VerticalSpacing.s12))
                    }
                }
            }

            // Add comment section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Padding.padding16),
                horizontalArrangement = Arrangement.spacedBy(Padding.padding8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newCommentText,
                    onValueChange = { newCommentText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Add a comment...") },
                    singleLine = true
                )
                Button(
                    onClick = {
                        // In a real app, this would call a use case to add comment
                        newCommentText = ""
                    },
                    enabled = newCommentText.isNotBlank()
                ) {
                    Text("Post")
                }
            }
        }
    }
}

@Composable
private fun CommentItem(
    comment: Comment,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Padding.padding16),
            horizontalArrangement = Arrangement.spacedBy(Padding.padding8)
        ) {
            // Placeholder avatar
            AsyncImage(
                model = "https://via.placeholder.com/40",
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(VerticalSpacing.s4)
            ) {
                Text(
                    text = "User ${comment.authorProfileId}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = comment.text,
                    fontSize = 14.sp
                )
            }
        }
    }
}
