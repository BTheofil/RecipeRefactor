package hu.tb.presentation.create

data class ProductCreateState(
    val isNameError: Boolean = false,
    val isQuantityError: Boolean = false
)
