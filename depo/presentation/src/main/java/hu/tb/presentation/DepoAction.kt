package hu.tb.presentation

import hu.tb.core.domain.product.Product

sealed interface DepoAction {
    object AddProductClick: DepoAction
    data class DeleteProduct(val product: Product): DepoAction
}