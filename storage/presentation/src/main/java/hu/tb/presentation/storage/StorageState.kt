package hu.tb.presentation.storage

import hu.tb.core.domain.meal.Category

data class StorageState(
    val categories: List<Category> = emptyList(),
    val foods: List<String> = emptyList(),
    val selectedGroupIndex: Int = 0,
)
