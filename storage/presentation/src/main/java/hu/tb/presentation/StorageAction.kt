package hu.tb.presentation

sealed interface StorageAction {
    object OnCategoryClick : StorageAction
    object OnDeleteCategoryClick: StorageAction
    object OnAddCategoryClick: StorageAction
}