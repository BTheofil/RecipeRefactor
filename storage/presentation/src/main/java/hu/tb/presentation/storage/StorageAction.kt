package hu.tb.presentation.storage

sealed interface StorageAction {
    object OnCategoryClick : StorageAction
    object OnDeleteCategoryClick: StorageAction
    object OnAddCategoryClick: StorageAction
    object OnAddFoodClick: StorageAction
}