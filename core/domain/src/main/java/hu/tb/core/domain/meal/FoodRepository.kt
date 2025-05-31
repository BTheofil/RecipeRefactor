package hu.tb.core.domain.meal

interface FoodRepository {

    suspend fun saveAll(categories: List<Category>)

    suspend fun delete(category: Category)

    suspend fun getAll(): List<Category>
}