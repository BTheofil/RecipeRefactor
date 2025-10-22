package hu.tb.shopping.presentation

import hu.tb.core.domain.shop.ShopItem

data class ShopState(
    val uncheckedItems: List<ShopItem> = emptyList(),
    val checkedItems: List<ShopItem> = emptyList()
)
