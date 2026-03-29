package com.bratyslav.common.model

import com.bratyslav.common.model.enums.Category

data class Purchase(
    val id: String,
    val name: String,
    val category: Category,
    val price: Long,
    val createdAt: Long // ms
)