package hu.tb.core.domain.recipe

import hu.tb.core.domain.product.Product

data class Recipe(
    val id: Long? = null,
    val name: String,
    val ingredients: List<Product>,
    val howToMakeSteps : List<String>
)