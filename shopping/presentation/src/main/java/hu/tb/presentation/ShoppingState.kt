package hu.tb.presentation

import hu.tb.domain.ShoppingItem

data class ShoppingState(
    val uncheckedItems: List<ShoppingItem> = emptyList(),
    val checkedItems: List<ShoppingItem> = emptyList()
)
