package hu.tb.shopping.presentation

import hu.tb.core.domain.shop.ShopItem

data class ShoppingState(
    val uncheckedItems: List<ShopItem> = emptyList(),
    val checkedItems: List<ShopItem> = emptyList()
)
