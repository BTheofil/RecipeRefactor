package hu.tb.core.domain.meal

import hu.tb.core.domain.util.NetworkError
import hu.tb.core.domain.util.Result

interface MealDataSource {

    suspend fun getRandomMeal(): Result<List<Meal>, NetworkError>

    suspend fun getMealById(id: Long)

    suspend fun getCategories()

    suspend fun getMealByFilter(filter: String)
}