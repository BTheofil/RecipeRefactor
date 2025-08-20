package hu.tb.shopping.presentation

sealed interface ShoppingEvent {
    data object ShowShoppingFinishedDialog: ShoppingEvent
}