package hu.tb.shopping.presentation

sealed interface ShopEvent {
    data object ShowShopFinishedDialog: ShopEvent
}