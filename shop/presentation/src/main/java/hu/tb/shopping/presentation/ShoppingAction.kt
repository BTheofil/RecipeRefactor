package hu.tb.shopping.presentation

import hu.tb.core.domain.product.Product
import hu.tb.core.domain.shop.ShopItem

sealed class ShoppingAction {
    data class SaveItem(val product: Product): ShoppingAction()
    data class ShopItemChange(val shopItem: ShopItem): ShoppingAction()
    data class OnDeleteSingleButtonClick(val item: ShopItem): ShoppingAction()
    data object OnClearButtonClick: ShoppingAction()
}
