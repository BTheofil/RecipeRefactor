package hu.tb.core.domain.meal

data class Food(
    val name: String,
    val category: FoodCategory,
    val quantity: Int,
    val measure: Measure
)
