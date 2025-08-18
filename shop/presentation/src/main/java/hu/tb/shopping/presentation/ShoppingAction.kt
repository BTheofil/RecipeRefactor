package hu.tb.shopping.presentation

import hu.tb.core.domain.shop.ShopItem

sealed class ShoppingAction {
    data object DeleteAllItems: ShoppingAction()
    data class ShopItemChange(val shopItem: ShopItem): ShoppingAction()
    data class DeleteItem(val item: ShopItem): ShoppingAction()
}
