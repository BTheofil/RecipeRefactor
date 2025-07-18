package hu.tb.core.domain.product

data class Product(
    val name: String,
    val quantity: Int,
    val measure: Measure
)