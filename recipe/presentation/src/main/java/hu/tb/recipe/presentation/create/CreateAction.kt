package hu.tb.recipe.presentation.create

import hu.tb.core.domain.product.Product

sealed interface CreateAction {

    sealed interface StarterAction: CreateAction {
        data object OnNextPage: StarterAction
        data class RecipeNameChange(val name: String): StarterAction
    }

    sealed interface IngredientsAction: CreateAction {
        data object OnNextPage : IngredientsAction
        data class OnAddIngredients(val product: Product) : IngredientsAction
        data class OnRemoveIngredient(val productIndex: Int) : IngredientsAction
    }
}