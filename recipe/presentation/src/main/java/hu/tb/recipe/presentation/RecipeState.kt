package hu.tb.recipe.presentation

import hu.tb.core.domain.recipe.Recipe

data class RecipeState(
    val recipes: List<Recipe> = emptyList(),
    val isMealsLoading: Boolean = false,
    val isErrorOccurred: Boolean = false,
    val isCategoriesLoading: Boolean = false,
    val isCategoryFailed: Boolean = false,
    val searchField: String = "",
    val isFilterMealLoading: Boolean = false
)
