package com.example.auth.presentation.registration

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.auth.domain.model.AccountType
import com.example.ui.theme.MallTheme
import com.example.ui.theme.Padding

@Composable
fun AccountTypeSwitch(
    selected: AccountType,
    onSelect: (AccountType) -> Unit,
    modifier: Modifier = Modifier
) {
    val cs = MaterialTheme.colorScheme
    val c = MallTheme.colors

    val shape = RoundedCornerShape(100)
    val height = 52.dp

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.padding20)
            .height(height)
            .clip(shape)
            .background(c.textSecondary)
            .padding(6.dp)
    ) {
        val segmentWidth = maxWidth / 2

        val animatedOffset by animateDpAsState(
            targetValue = if (selected == AccountType.USER) 0.dp else segmentWidth,
            animationSpec = tween(220),
            label = "segmentOffset"
        )


        Box(
            modifier = Modifier
                .offset(x = animatedOffset)
                .width(segmentWidth)
                .fillMaxHeight()
                .clip(shape)
                .background(cs.surface)
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SegmentText(
                text = "User",
                onClick = { onSelect(AccountType.USER) },
                modifier = Modifier.weight(1f),
                color = if (selected == AccountType.USER) cs.onSurface else c.surface
            )
            SegmentText(
                text = "Business",
                onClick = { onSelect(AccountType.BUSINESS) },
                modifier = Modifier.weight(1f),
                color = if (selected == AccountType.BUSINESS) cs.onSurface else c.surface
            )
        }
    }
}

@Composable
private fun SegmentText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .clickable(onClick = onClick)
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = color)
    }
}