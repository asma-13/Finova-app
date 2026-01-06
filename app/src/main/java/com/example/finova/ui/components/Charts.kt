package com.example.finova.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.finova.ui.theme.FinovaBlack
import com.example.finova.ui.theme.FinovaCream
import com.example.finova.ui.theme.FinovaGold
import com.example.finova.ui.theme.FinovaTheme
import kotlin.random.Random

@Composable
fun ProgressRing(
    progress: Float,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 8.dp,
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val currentProgress by animateFloatAsState(
        targetValue = if (animationPlayed) progress else 0f,
        animationSpec = tween(durationMillis = 1000, delayMillis = 200),
        label = "ProgressAnimation"
    )

    LaunchedEffect(Unit) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .drawBehind {
                drawRing(
                    color = FinovaBlack,
                    sweepAngle = 360f,
                    strokeWidth = strokeWidth
                )
                drawRing(
                    color = FinovaGold,
                    sweepAngle = 360 * currentProgress,
                    strokeWidth = strokeWidth,
                    strokeCap = StrokeCap.Round
                )
            }
    ) {
        Text(
            text = "${(currentProgress * 100).toInt()}%",
            color = FinovaCream,
            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun LineChart(
    data: List<Double>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val xSpacing = size.width / (data.size - 1)
        val maxVal = data.maxOrNull() ?: 0.0

        val strokePath = Path().apply {
            moveTo(0f, size.height - (data.first().toFloat() / maxVal.toFloat() * size.height))
            data.indices.drop(1).forEach { i ->
                val x = i * xSpacing
                val y = size.height - (data[i].toFloat() / maxVal.toFloat() * size.height)
                lineTo(x, y)
            }
        }

        val fillPath = Path().apply {
            addPath(strokePath)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }

        drawPath(
            path = strokePath,
            color = FinovaGold,
            style = Stroke(width = 3.dp.toPx())
        )

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    FinovaGold.copy(alpha = 0.4f),
                    Color.Transparent
                ),
                endY = size.height
            )
        )
    }
}

@Composable
fun PieChart(
    data: Map<String, Double>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    val total = data.values.sum()
    var startAngle = -90f

    Canvas(modifier = modifier) {
        data.entries.forEachIndexed { index, entry ->
            val sweepAngle = (entry.value / total).toFloat() * 360f
            drawArc(
                color = colors[index % colors.size],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = Size(size.width, size.height)
            )
            startAngle += sweepAngle
        }
    }
}

private fun DrawScope.drawRing(
    color: Color,
    sweepAngle: Float,
    strokeWidth: Dp,
    strokeCap: StrokeCap = StrokeCap.Butt
) {
    drawArc(
        color = color,
        startAngle = -90f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(width = strokeWidth.toPx(), cap = strokeCap),
        size = Size(size.width, size.height),
        topLeft = Offset.Zero
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF420B0B)
@Composable
private fun ChartsPreview() {
    FinovaTheme {
        val pieChartData = mapOf(
            "Groceries" to 120.5,
            "Transport" to 75.0,
            "Entertainment" to 250.75,
            "Utilities" to 90.0
        )
        val pieChartColors = listOf(Color.Red, Color.Green, Color.Blue, Color.Magenta)

        PieChart(
            data = pieChartData,
            colors = pieChartColors,
            modifier = Modifier.size(200.dp)
        )
    }
}
