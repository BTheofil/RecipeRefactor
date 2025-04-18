package hu.tb.recipe.presentation

import hu.tb.core.domain.meal.Meal

data class RecipeState(
    val meals: List<Meal> = emptyList(),
    val isMealsLoading: Boolean = false,
    val isErrorOccurred: Boolean = false,
    val searchField: String = "",
)
