package hu.tb.core.domain.meal

interface FoodRepository {

    suspend fun insert(food: Food)

    suspend fun delete(food: Food)

    suspend fun getAll(): List<Food>
}