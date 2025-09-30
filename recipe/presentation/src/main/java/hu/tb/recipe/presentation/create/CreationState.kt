package hu.tb.recipe.presentation.create

import hu.tb.core.domain.product.Product

data class CreationState(
    val recipeName: String = "",
    val isRecipeNameHasError: Boolean = false,
    val ingredients: List<Product> = emptyList(),
    val isIngredientsHasError: Boolean = false,
    val steps: List<String> = listOf(""),
    val isStepsHasError: Boolean = false,
    val productsInDepo: List<Product> = emptyList()
)
