package hu.tb.presentation

data class StorageState(
    val categories: List<String> = emptyList(),
    val foods: List<String> = emptyList(),
    val selectedGroupIndex: Int = 0,
)
