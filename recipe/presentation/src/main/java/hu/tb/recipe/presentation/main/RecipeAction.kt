package hu.tb.recipe.presentation.main

sealed class RecipeAction {
    data class RecipeClick(val recipeId: Long): RecipeAction()
    data object CreateRecipeClick: RecipeAction()
}