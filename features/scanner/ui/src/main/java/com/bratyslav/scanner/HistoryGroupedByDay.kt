package com.bratyslav.scanner

import com.bratyslav.common.model.Purchase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class HistoryGroupedByDay(
    val date: String, // Formatted date string (e.g., "Dec 15, 2024")
    val dateTimestamp: Long, // Start of day timestamp for sorting
    val items: List<Purchase>
) {
    companion object {
        fun groupByDay(items: List<Purchase>): List<HistoryGroupedByDay> {
            val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val dayFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            return items
                .groupBy { purchase ->
                    // Group by day (ignore time)
                    val date = Date(purchase.createdAt)
                    dayFormatter.format(date)
                }
                .map { (dayKey, dayItems) ->
                    val firstItem = dayItems.first()
                    val date = Date(firstItem.createdAt)
                    val startOfDay = dayFormatter.parse(dayKey)?.time ?: firstItem.createdAt

                    HistoryGroupedByDay(
                        date = dateFormatter.format(date),
                        dateTimestamp = startOfDay,
                        items = dayItems.sortedByDescending { it.createdAt }
                    )
                }
                .sortedByDescending { it.dateTimestamp }
        }
    }
}