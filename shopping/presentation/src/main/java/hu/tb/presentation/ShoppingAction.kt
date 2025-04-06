package hu.tb.presentation

import hu.tb.domain.ShoppingItem

sealed class ShoppingAction {
    data class OnItemCheckChange(val item: ShoppingItem, val change: Boolean): ShoppingAction()
    data object OnClearButtonClick: ShoppingAction()
    data class OnCreateDialogSaveButtonClick(val newItem: String): ShoppingAction()
}
