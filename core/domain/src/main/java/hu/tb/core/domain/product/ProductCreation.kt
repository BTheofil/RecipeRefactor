package hu.tb.core.domain.product

import hu.tb.core.domain.shop.ShopItem

data class ProductCreation(
    val id: Long? = null,
    val name: String,
    val quantity: Double,
    val measure: Measure
) {
    fun toProduct() =
        Product(
            name = name,
            quantity = quantity,
            measure = measure
        )

    fun toShopItem() =
        ShopItem(id, name, quantity, measure)
}