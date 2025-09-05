package hu.tb.shopping.presentation

import hu.tb.core.domain.shop.ShopItem

sealed class ShopAction {
    data object DeleteAllItems: ShopAction()
    data object AddAllItemsToStorage: ShopAction()
    data class ShopItemChange(val shopItem: ShopItem): ShopAction()
    data class DeleteItem(val item: ShopItem): ShopAction()
}
