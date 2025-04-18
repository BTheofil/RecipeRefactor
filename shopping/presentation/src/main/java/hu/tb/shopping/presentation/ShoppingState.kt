package hu.tb.shopping.presentation

import hu.tb.core.domain.shopping.ShoppingItem

data class ShoppingState(
    val uncheckedItems: List<ShoppingItem> = emptyList(),
    val checkedItems: List<ShoppingItem> = emptyList()
)
