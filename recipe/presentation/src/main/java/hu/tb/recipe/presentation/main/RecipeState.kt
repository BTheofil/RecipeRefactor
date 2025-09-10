package hu.tb.recipe.presentation.main

import hu.tb.core.domain.recipe.Recipe

data class RecipeState(
    val recipes: List<Recipe> = emptyList(),
)
