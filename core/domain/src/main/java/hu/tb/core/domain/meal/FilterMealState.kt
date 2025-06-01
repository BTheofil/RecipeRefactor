package hu.tb.core.domain.meal

data class FilterMealState(
    val category: Category = Category("Beef"),
    val filterMealList: List<FilterMeal> = emptyList()
)