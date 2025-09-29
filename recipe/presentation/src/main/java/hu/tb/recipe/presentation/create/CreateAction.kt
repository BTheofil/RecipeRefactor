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

    sealed interface StepsAction: CreateAction {
        data object AddStepField: StepsAction
        data object FinishSteps: StepsAction
        data class RemoveStep(val index: Int): StepsAction
        data class StepFieldChange(val index: Int, val text: String): StepsAction
    }
}