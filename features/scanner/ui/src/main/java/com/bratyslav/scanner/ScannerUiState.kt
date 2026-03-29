package com.bratyslav.scanner

import com.bratyslav.common.model.Purchase

data class ScannerUiState(
    val photoUri: String? = null,
    val isPermissionDenied: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val parsedItems: List<Purchase> = emptyList(),
    val localHistory: List<Purchase> = emptyList(),
    val startDate: Long? = null,
    val endDate: Long? = null
)
