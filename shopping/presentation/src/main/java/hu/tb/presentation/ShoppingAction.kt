package hu.tb.presentation

import hu.tb.domain.ShoppingItem

sealed class ShoppingAction {
    data class OnItemCheckChange(val item: ShoppingItem): ShoppingAction()
    data class OnEditItemChange(val item: ShoppingItem): ShoppingAction()
    data class OnCreateDialogSaveButtonClick(val newItem: ShoppingItem): ShoppingAction()
    data class OnDeleteSingleButtonClick(val item: ShoppingItem): ShoppingAction()
    data object OnClearButtonClick: ShoppingAction()
}
