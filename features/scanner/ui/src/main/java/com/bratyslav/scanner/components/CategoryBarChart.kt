package com.bratyslav.scanner.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bratyslav.common.R
import com.bratyslav.scanner.CategoryTotalUi

@Composable
fun CategoryBarChart(
    totals: List<CategoryTotalUi>,
    modifier: Modifier = Modifier,
    maxBarHeight: Dp = 160.dp,
    barSpacing: Dp = 4.dp
) {
    val maxVal = (totals.maxOfOrNull { it.totalMinor } ?: 0L).coerceAtLeast(1L)
    if (totals.all { it.totalMinor == 0L }) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No data yet",
                color = Color.DarkGray
            )
        }
        return
    }

    Column(modifier = modifier) {
        // Bars area
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxBarHeight),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(barSpacing)
        ) {
            totals.forEach { item ->
                val ratio = (item.totalMinor.toFloat() / maxVal.toFloat())
                val targetHeight = maxBarHeight * ratio
                val animatedHeight by animateDpAsState(targetValue = targetHeight, label = "barHeight")

                Column(
                    modifier = Modifier.weight(1f, fill = true),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    // value label
                    if (item.totalMinor > 0) {
                        Text(
                            text = format(item.totalMinor),
                            color = Color.Black,
                            fontSize = 11.sp,
                            maxLines = 1
                        )
                        Spacer(Modifier.height(4.dp))
                    } else {
                        Spacer(Modifier.height(20.dp))
                    }

                    // bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(animatedHeight)
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        colorResource(R.color.secondary),
                                        colorResource(R.color.primary)
                                    )
                                )
                            )
                    )
                }
            }
        }

        Spacer(Modifier.height(4.dp))

        // X labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(barSpacing)
        ) {
            var isFirst = true
            totals.forEach { item ->
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.category.name,
                        color = Color.Black,
                        fontSize = 8.sp,
                        maxLines = 1
                    )
                }
                isFirst = false
            }
        }
    }
}

private fun format(minor: Long): String {
    val whole = minor / 100
    return "$whole"
}