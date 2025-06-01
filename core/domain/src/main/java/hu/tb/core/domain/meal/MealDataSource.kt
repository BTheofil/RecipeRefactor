package hu.tb.core.domain.meal

import hu.tb.core.domain.util.NetworkError
import hu.tb.core.domain.util.Result
import kotlinx.coroutines.flow.StateFlow

interface MealDataSource {

    val filterMeals: StateFlow<FilterMealState>

    suspend fun getRandomMeal(): Result<List<Meal>, NetworkError>

    suspend fun getMealById(id: Long)

    suspend fun getCategories(): Result<List<Category>, NetworkError>

    suspend fun getMealByFilter(category: Category): Result<List<FilterMeal>, NetworkError>
}