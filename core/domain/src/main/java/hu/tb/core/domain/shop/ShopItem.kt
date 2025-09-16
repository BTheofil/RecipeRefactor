package hu.tb.core.domain.shop

import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.product.ProductCreated

data class ShopItem(
    val id: Long? = null,
    val name: String,
    val quantity: Double,
    val measure: Measure,
    val isChecked: Boolean = false
) {
    fun toProductCreation() =
        ProductCreated(
            id, name, quantity, measure
        )
}