package hu.tb.shopping.presentation

import hu.tb.core.domain.shop.ShopItem

sealed class ShoppingAction {
    data class OnItemCheckChange(val item: ShopItem): ShoppingAction()
    data class OnEditItemChange(val item: ShopItem): ShoppingAction()
    data class OnCreateDialogSaveButtonClick(val newItem: ShopItem): ShoppingAction()
    data class OnDeleteSingleButtonClick(val item: ShopItem): ShoppingAction()
    data object OnClearButtonClick: ShoppingAction()
}
