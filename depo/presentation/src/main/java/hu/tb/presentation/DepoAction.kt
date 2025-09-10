package hu.tb.presentation

sealed interface DepoAction {
    object OnAddFoodClick: DepoAction
}