package hu.tb.domain

data class ShoppingItem(
    val id: Long? = null,
    val name: String,
    val isChecked: Boolean = false
)