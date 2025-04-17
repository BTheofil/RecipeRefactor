package hu.tb.core.data.network

import kotlinx.serialization.Serializable

@Serializable
data class MealDto(
    val meals: List<Meal>
)