package hu.tb.core.domain.shopping

data class ShoppingItem(
    val id: Long? = null,
    val name: String,
    val isChecked: Boolean = false
)