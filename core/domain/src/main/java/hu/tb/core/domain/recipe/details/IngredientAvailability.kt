package hu.tb.core.domain.recipe.details

import hu.tb.core.domain.product.Product

data class IngredientAvailability(
    val product: Product,
    val availability: Availability = Availability.UNKNOWN
)

enum class Availability {
    HAVE, LESS, UNKNOWN
}