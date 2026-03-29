package com.bratyslav.scanner

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bratyslav.common.model.Purchase
import com.bratyslav.common.model.enums.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow(ScannerUiState())
    val state: StateFlow<ScannerUiState> = _state.asStateFlow()

    fun updatePermissionDenied(denied: Boolean) {
        _state.update { it.copy(isPermissionDenied = denied) }
    }

    fun updateImageUri(newValue: String?) {
        _state.update { it.copy(photoUri = newValue) }
    }

    fun initializeLocalHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            // _state.update { it.copy(localHistory = openaiRepository.getCachedHistory()) }
            _state.update { it.copy(localHistory = fakeDebugPurchases()) }
        }
    }

    fun parseReceipt(uri: Uri) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _state.update {
//                state.value.copy(isLoading = true, error = null)
//            }
//            val base64Jpeg = uri.imageUriToBase64Jpeg(context = application)
//            val result = openaiRepository.parseReceipt(base64Jpeg)
//            val data = result.getOrNull()
//            if (result.isSuccess && data != null) {
//                _state.update {
//                    state.value.copy(
//                        isLoading = false,
//                        parsedItems = data,
//                        localHistory = openaiRepository.getCachedHistory() // update history
//                    )
//                }
//            } else {
//                _state.update {
//                    state.value.copy(
//                        isLoading = false,
//                        error = result.exceptionOrNull()?.message ?: "unknown error"
//                    )
//                }
//            }
//        }
    }

    fun clearParsedItems() {
        _state.update { it.copy(parsedItems = emptyList()) }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }

    fun updateDateRange(startDate: Long?, endDate: Long?) {
        _state.update { it.copy(startDate = startDate, endDate = endDate) }
    }

    fun clearDateRange() {
        _state.update { it.copy(startDate = null, endDate = null) }
    }

    fun getDateRangeLocalHistory(): List<Purchase> = state.value.localHistory.filter { purchase ->
        val createdAt = purchase.createdAt
        val startDate = state.value.startDate ?: Long.MIN_VALUE
        val endDate = state.value.endDate ?: Long.MAX_VALUE
        createdAt >= startDate && createdAt <= endDate
    }
}

private fun fakeDebugPurchases(): List<Purchase> {
    val now = System.currentTimeMillis()
    val dayMs = 24L * 60 * 60 * 1000
    return listOf(
        Purchase("dbg-1", "Supermarket", Category.Food, 45_67L, now - dayMs * 2),
        Purchase("dbg-2", "Coffee", Category.Food, 5_50L, now - dayMs),
        Purchase("dbg-3", "Rent", Category.House, 100_00L, now - dayMs * 3),
        Purchase("dbg-4", "Bus pass", Category.Ride, 35_00L, now - dayMs * 2),
        Purchase("dbg-5", "Pharmacy", Category.Health, 12_99L, now - dayMs),
        Purchase("dbg-6", "Cinema", Category.Fun, 18_00L, now - dayMs * 4),
        Purchase("dbg-7", "T-shirt", Category.Clothes, 29_99L, now - dayMs),
        Purchase("dbg-8", "Wine", Category.Alcohol, 15_00L, now - dayMs * 5),
        Purchase("dbg-9", "Pet food", Category.Pets, 42_00L, now - dayMs * 2),
        Purchase("dbg-10", "Gift", Category.Other, 25_00L, now - dayMs),
    )
}