package com.bratyslav.scanner.screens

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bratyslav.scanner.ScannerViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.bratyslav.common.utils.createImageUri
import com.bratyslav.common.utils.decodeSampledBitmapFromUri
import com.bratyslav.common.utils.formatAll
import com.bratyslav.scanner.CategoryTotalUi.Companion.buildCategoryTotals
import com.bratyslav.scanner.components.CategoryBarChart
import com.bratyslav.scanner.components.DateRangePicker
import com.bratyslav.scanner.components.ParsingProgressBar
import com.bratyslav.common.R

@Composable
fun ScannerScreen(
    viewModel: ScannerViewModel = hiltViewModel(),
    onHistoryClick: () -> Unit = {},
    onPreviewClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState = viewModel.state.collectAsState().value
    // Local preview bitmap only (ephemeral)
    var preview by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val photoUri = uiState.photoUri?.let(Uri::parse)
    val dateRangeTotal = formatAll(remember(uiState.localHistory, uiState.startDate, uiState.endDate) {
        viewModel.getDateRangeLocalHistory().sumOf { it.price }
    })

    LaunchedEffect(Unit) {
        viewModel.initializeLocalHistory()
    }

    // Navigate to preview when parsing is complete
    LaunchedEffect(uiState.parsedItems.isNotEmpty()) {
        if (uiState.parsedItems.isNotEmpty()) {
            onPreviewClick()
        }
    }

    val takePicture = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && photoUri != null) {
            preview = decodeSampledBitmapFromUri(context, photoUri, maxSize = 1600)
            // Clear any previous error when taking a new photo
            viewModel.clearError()
        } else {
            viewModel.updateImageUri(null)
        }
    }

    val requestCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        viewModel.updatePermissionDenied(!granted)
        if (granted) {
            val uri = createImageUri(context)
            viewModel.updateImageUri(uri.toString())
            takePicture.launch(uri)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(id = com.bratyslav.scanner.ui.R.drawable.ic_history),
                contentDescription = "History",
                tint = colorResource(R.color.primary_dark),
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterStart)
                    .clickable { onHistoryClick() }
            )

            DateRangePicker(
                startDate = uiState.startDate,
                endDate = uiState.endDate,
                onDateRangeChanged = { s, e -> viewModel.updateDateRange(s, e) }
            )

            // Clear Button
            if (uiState.startDate != null || uiState.endDate != null) {
                Text(
                    text = "Clear",
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = colorResource(R.color.primary_dark),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { viewModel.updateDateRange(null, null) }
                        .padding(bottom = 1.5.dp)
                )
            }
        }

        Text(
            text = "Total: $dateRangeTotal",
            fontWeight = FontWeight.Light,
            fontSize = 15.sp,
            color = colorResource(R.color.primary_dark),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        CategoryBarChart(
            totals = remember(uiState.localHistory, uiState.startDate, uiState.endDate) {
                buildCategoryTotals(viewModel.getDateRangeLocalHistory())
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )

        // Parse Flow Bottom Sheet
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .background(
                    color = colorResource(R.color.background_2),
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .padding(start = 20.dp, end = 20.dp, top = 30.dp, bottom = 20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(bottom = 60.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    when {
                        uiState.isPermissionDenied -> Text(
                            text = "Camera permission denied. Enable it in Settings to continue.",
                            color = MaterialTheme.colorScheme.error
                        )

                        uiState.error != null -> Text(
                            text = "Error: ${uiState.error}",
                            color = Color.Red
                        )

                        preview != null && photoUri != null -> {
                            // todo
                            ParsingProgressBar(preview)
                            // Run LMM
                            LaunchedEffect(photoUri) {
                                viewModel.parseReceipt(photoUri)
                            }
                        }

                        else -> Text(
                            text = "Use the camera to scan new receipt",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.DarkGray
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .shadow(1.dp, shape = RoundedCornerShape(30.dp))
                    .background(color = colorResource(R.color.primary), shape = RoundedCornerShape(30.dp))
                    .clickable { requestCamera.launch(Manifest.permission.CAMERA) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = com.bratyslav.scanner.ui.R.drawable.ic_camera),
                    contentDescription = "Camera",
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScannerRoutePreview() {
//    MaterialTheme {
//        ScannerScreen()
//    }
}
