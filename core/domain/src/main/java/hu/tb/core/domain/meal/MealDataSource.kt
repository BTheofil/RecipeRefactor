package hu.tb.core.domain.meal

interface MealDataSource {

    suspend fun getRandomMeal()

    suspend fun getMealById(id: Long)

    suspend fun getCategories()

    suspend fun getMealByFilter(filter: String)
}