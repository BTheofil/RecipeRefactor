package hu.tb.recipe.presentation.main

sealed class RecipeAction {
    data object CreateRecipeClick: RecipeAction()
}