package com.example.ui.theme.common


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LogoLoader(
    modifier: Modifier = Modifier,
    logoRes: Int,
    size: Dp = 84.dp,
    ringColor: Color = Color.White,
    backgroundColor: Color = Color.Transparent,
    segments: Int = 12,
    speedMsPerRound: Int = 900,
) {
    val infinite = rememberInfiniteTransition(label = "chatgpt_like_loader")

    val rot by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = speedMsPerRound, easing = LinearEasing)
        ),
        label = "rot"
    )

    val pulse by infinite.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = LinearEasing),
        ),
        label = "pulse"
    )

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .scale(pulse)
        ) {
            val strokeWidth = this.size.minDimension * 0.085f
            val pad = strokeWidth / 2f

            val arcSize = Size(
                width = this.size.width - pad * 2f,
                height = this.size.height - pad * 2f
            )
            val topLeft = Offset(pad, pad)

            val segmentSweep = 360f / segments
            val gap = segmentSweep * 0.45f // space between segments (controls “dotty” look)
            val drawnSweep = segmentSweep - gap

            val head = (rot / segmentSweep)

            for (i in 0 until segments) {

                val dist = circularDistance(i.toFloat(), head, segments.toFloat())

                val alpha = when {
                    dist < 0.0f -> 0f
                    else -> {
                        val x = (1f - (dist / (segments * 0.55f))).coerceIn(0f, 1f)
                        x * x
                    }
                }

                if (alpha <= 0.01f) continue

                val start = i * segmentSweep + rot - 90f

                drawArc(
                    color = ringColor.copy(alpha = alpha),
                    startAngle = start,
                    sweepAngle = drawnSweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
        }

        Image(
            painter = painterResource(logoRes),
            contentDescription = "Loading",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .scale(0.86f)
                .clip(CircleShape)
        )
    }
}


private fun circularDistance(i: Float, head: Float, n: Float): Float {
    // put i "behind" head
    var d = head - i
    while (d < 0f) d += n
    while (d >= n) d -= n
    return d
}