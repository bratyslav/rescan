package com.bratyslav.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import kotlin.math.max

fun formatAll(minor: Long): String {
    val sign = if (minor < 0) "-" else ""
    val abs = kotlin.math.abs(minor)
    val whole = abs / 100
    val cents = (abs % 100).toInt()
    return "$sign$whole.${cents.toString().padStart(2, '0')}"
}

fun decodeSampledBitmapFromUri(
    context: Context,
    uri: Uri,
    maxSize: Int
): Bitmap? {
    // 1) bounds
    val opts1 = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    context.contentResolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, opts1) }

    val (w, h) = opts1.outWidth to opts1.outHeight
    if (w <= 0 || h <= 0) return null

    // 2) calculate inSampleSize
    var inSampleSize = 1
    val maxDim = max(w, h)
    if (maxDim > maxSize) {
        inSampleSize = maxDim / maxSize
        if (inSampleSize < 1) inSampleSize = 1
    }

    // 3) decode
    val opts2 = BitmapFactory.Options().apply { this.inSampleSize = inSampleSize }
    return context.contentResolver.openInputStream(uri)?.use {
        BitmapFactory.decodeStream(it, null, opts2)
    }
}

fun createImageUri(context: Context): Uri {
    // Store in cache/camera; FileProvider exposes it to the camera app
    val dir = File(context.cacheDir, "camera").apply { mkdirs() }
    val file = File.createTempFile("receipt_", ".jpg", dir)
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
}