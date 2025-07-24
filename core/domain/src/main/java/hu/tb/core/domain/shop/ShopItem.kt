package hu.tb.core.domain.shop

data class ShopItem(
    val id: Long? = null,
    val name: String,
    val isChecked: Boolean = false
)