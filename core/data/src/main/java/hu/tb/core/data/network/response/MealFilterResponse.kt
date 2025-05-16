package hu.tb.core.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealFilterResponse(
    @SerialName("meals")
    val filteredMeals: List<FilterMealDto>
)

@Serializable
data class FilterMealDto(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)