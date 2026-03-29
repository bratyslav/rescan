package com.bratyslav.scanner.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bratyslav.common.R
import kotlinx.coroutines.delay

@Composable
fun ParsingProgressBar(
    preview: Bitmap?
) {
    val totalTimeParsing = 25
    // Timer state for circular progress bar
    var timeRemaining by remember { mutableIntStateOf(totalTimeParsing) }
    var isTimerRunning by remember { mutableStateOf(false) }
    // Start timer when preview becomes available
    LaunchedEffect(preview) {
        if (preview != null && !isTimerRunning) {
            timeRemaining = totalTimeParsing
            isTimerRunning = true

            while (timeRemaining > 0) {
                delay(1000) // Wait 1 second
                timeRemaining--
            }
            isTimerRunning = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressBar(
                progress = (totalTimeParsing - timeRemaining) / totalTimeParsing.toFloat(),
                modifier = Modifier
                    .size(200.dp),
                color = colorResource(R.color.primary),
                strokeWidth = 6f,
                size = 510f
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.size(10.dp))

            Image(
                bitmap = preview?.asImageBitmap() ?: return,
                contentDescription = "Receipt preview",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .rotate(90f)
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Analyzing...",
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = Color.DarkGray
            )
        }
    }
}