package hu.tb.core.domain.product

data class Product(
    val id: Long? = null,
    val name: String,
    val quantity: Double,
    val measure: Measure
)