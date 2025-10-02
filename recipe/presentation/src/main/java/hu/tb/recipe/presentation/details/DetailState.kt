package hu.tb.recipe.presentation.details

import hu.tb.core.domain.recipe.Recipe
import hu.tb.core.domain.recipe.details.IngredientAvailability

data class DetailState(
    val recipe: Recipe? = null,
    val recipeIngredientsResult: List<IngredientAvailability> = emptyList(),
)