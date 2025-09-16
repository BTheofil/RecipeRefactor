package hu.tb.core.domain.product

import hu.tb.core.domain.shop.ShopItem

data class ProductCreated(
    val id: Long? = null,
    val name: String,
    val quantity: Double? = null,
    val measure: Measure? = null
) {
    fun toProduct(): Product {
        require(quantity != null && measure != null) { "quantity or measure can not be null" }
        return Product(
            name = name,
            quantity = quantity,
            measure = measure
        )
    }


    fun toShopItem(): ShopItem {
        require(quantity != null && measure != null) { "quantity or measure can not be null" }
        return ShopItem(id, name, quantity, measure)
    }
}