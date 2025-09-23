package hu.tb.presentation.create

import hu.tb.core.domain.product.Product

sealed class ProductCreateAction {
    data object CloseScreen: ProductCreateAction()
    data class AddNewProduct(val product: Product): ProductCreateAction()
}