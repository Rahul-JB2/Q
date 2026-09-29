package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularProgressBadge(
    progressPercent: Int,
    size: Dp = 64.dp,
    strokeWidth: Dp = 6.dp,
    progressColor: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    modifier: Modifier = Modifier
) {
    val sweepFraction by animateFloatAsState(
        targetValue = (progressPercent.coerceIn(0, 100)) / 100f,
        label = "progressSweep"
    )

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            // Background track
            drawCircle(
                color = trackColor,
                radius = (size.toPx() - strokeWidth.toPx()) / 2f,
                style = stroke
            )
            // Foreground progress arc
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = 360f * sweepFraction,
                useCenter = false,
                style = stroke
            )
        }
        Text(
            text = "$progressPercent%",
            fontSize = (size.value * 0.22f).sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun LabeledProgressBar(
    label: String,
    current: Int,
    total: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    val progress = if (total > 0) (current.toFloat() / total).coerceIn(0f, 1f) else 0f
    val percent = (progress * 100).toInt()

    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                text = "$current / $total ($percent%)",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}
