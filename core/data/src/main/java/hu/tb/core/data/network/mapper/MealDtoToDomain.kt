package hu.tb.core.data.network.mapper

import hu.tb.core.data.network.MealDto
import hu.tb.core.domain.meal.Meal

fun MealDto.toDomain(): Meal = Meal(
    id = idMeal,
    meal = strMeal,
    category = strCategory,
    ingredients = pairIngredientsWithMeasure(),
    image = strMealThumb
)