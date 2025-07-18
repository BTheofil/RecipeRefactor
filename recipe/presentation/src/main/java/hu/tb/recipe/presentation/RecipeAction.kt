package hu.tb.recipe.presentation

sealed class RecipeAction {
    data class OnSearchTextChange(val text: String) : RecipeAction()
    data class OnSearch(val searchText: String) : RecipeAction()
}