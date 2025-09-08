package hu.tb.presentation.storage

import hu.tb.core.domain.product.Product

data class StorageState(
    val products: List<Product> = emptyList(),
    val selectedGroupIndex: Int = 0,
)
