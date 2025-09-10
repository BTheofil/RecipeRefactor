package hu.tb.presentation.storage

sealed interface DepoAction {
    object OnAddFoodClick: DepoAction
}