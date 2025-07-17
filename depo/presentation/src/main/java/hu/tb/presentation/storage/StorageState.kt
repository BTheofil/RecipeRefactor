package hu.tb.presentation.storage

data class StorageState(
    val foods: List<String> = emptyList(),
    val selectedGroupIndex: Int = 0,
)
