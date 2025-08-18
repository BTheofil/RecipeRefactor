package hu.tb.core.domain.shop

import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.product.ProductCreation

data class ShopItem(
    val id: Long? = null,
    val name: String,
    val quantity: Int,
    val measure: Measure,
    val isChecked: Boolean = false
) {
    fun toProductCreation() =
        ProductCreation(
            id, name, quantity, measure
        )
}