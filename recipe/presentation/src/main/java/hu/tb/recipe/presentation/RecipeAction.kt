package hu.tb.recipe.presentation

import hu.tb.core.domain.meal.Category

sealed class RecipeAction {
    data class OnSearchTextChange(val text: String) : RecipeAction()
    data class OnSearch(val searchText: String) : RecipeAction()
    data class OnFilterCategoryClick(val category: Category): RecipeAction()
}