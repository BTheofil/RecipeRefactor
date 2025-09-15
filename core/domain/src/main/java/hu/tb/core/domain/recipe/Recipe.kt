package hu.tb.core.domain.recipe

import hu.tb.core.domain.product.Product
import hu.tb.core.domain.step.Step

data class Recipe(
    val id: Long? = null,
    val name: String,
    val ingredients: List<Product>,
    val howToMakeSteps : List<Step>
)