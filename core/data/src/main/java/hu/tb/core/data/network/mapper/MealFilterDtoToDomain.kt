package hu.tb.core.data.network.mapper

import hu.tb.core.data.network.response.FilterMealDto
import hu.tb.core.domain.meal.FilterMeal

fun FilterMealDto.toDomain(): FilterMeal = FilterMeal(
    id = idMeal,
    name = strMeal,
    image = strMealThumb
)