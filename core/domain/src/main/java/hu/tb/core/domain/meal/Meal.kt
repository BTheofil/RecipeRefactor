package hu.tb.core.domain.meal

data class Meal(
    val id: String,
    val meal: String,
    val category: String,
    val ingredients: List<Pair<String, String>>,
    val image: String,
)
