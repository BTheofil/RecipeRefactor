package hu.tb.presentation.storage

sealed interface StorageAction {
    object OnAddFoodClick: StorageAction
}