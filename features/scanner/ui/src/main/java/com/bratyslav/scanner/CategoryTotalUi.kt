package com.bratyslav.scanner

import com.bratyslav.common.model.Purchase
import com.bratyslav.common.model.enums.Category

data class CategoryTotalUi(
    val category: Category,
    val totalMinor: Long
) {
    companion object {
        fun buildCategoryTotals(items: List<Purchase>): List<CategoryTotalUi> {
            val sums = items.groupBy { it.category }
                .mapValues { (_, list) -> list.sumOf { it.price } }

            return CATEGORY_ORDER.map { cat ->
                CategoryTotalUi(category = cat, totalMinor = sums[cat] ?: 0L)
            }
        }

        private val CATEGORY_ORDER = listOf(
            Category.Food,
            Category.House,
            Category.Ride,
            Category.Health,
            Category.Fun,
            Category.Clothes,
            Category.Alcohol,
            Category.Pets,
            Category.Other
        )
    }
}