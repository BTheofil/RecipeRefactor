package hu.tb.core.domain.product

import hu.tb.core.domain.shop.ShopItem

data class ProductCreation(
    val id: Long? = null,
    val name: String,
    val quantity: Int,
    val measure: Measure
) {
    fun toProduct() =
        Product(
            name, quantity, measure
        )

    fun toShopItem() =
        ShopItem(id, name, quantity, measure)
}