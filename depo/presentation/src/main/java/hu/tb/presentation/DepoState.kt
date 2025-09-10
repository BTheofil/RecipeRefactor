package hu.tb.presentation

import hu.tb.core.domain.product.Product

data class DepoState(
    val products: List<Product> = emptyList(),
    val selectedGroupIndex: Int = 0,
)
