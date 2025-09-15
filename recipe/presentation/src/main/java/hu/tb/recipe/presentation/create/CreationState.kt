package hu.tb.recipe.presentation.create

import hu.tb.core.domain.product.Product

data class CreationState(
    val recipeName: String = "",
    val ingredients: List<Product> = emptyList(),
    val steps: List<String> = listOf(""),
    val productsInDepo: List<Product> = emptyList()
)
