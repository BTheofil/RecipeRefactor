package hu.tb.recipe.presentation

import hu.tb.core.domain.meal.Category
import hu.tb.core.domain.meal.FilterMeal
import hu.tb.core.domain.meal.Meal

data class RecipeState(
    val meals: List<Meal> = emptyList(),
    val isMealsLoading: Boolean = false,
    val isErrorOccurred: Boolean = false,
    val categories: List<Category> = emptyList(),
    val isCategoriesLoading: Boolean = false,
    val searchField: String = "",
    val selectedFilter: String = "Beef",
    val filterMeals: List<FilterMeal> = emptyList(),
    val isFilterMealLoading: Boolean = false
)
