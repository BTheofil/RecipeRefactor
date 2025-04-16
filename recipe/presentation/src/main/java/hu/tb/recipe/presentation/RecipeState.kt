package hu.tb.recipe.presentation

data class RecipeState(
    val recipes: List<String> = emptyList(),
    val searchField: String = "",
)
