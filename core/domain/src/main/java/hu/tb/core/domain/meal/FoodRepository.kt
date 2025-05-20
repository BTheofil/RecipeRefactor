package hu.tb.core.domain.meal

interface FoodRepository {

    suspend fun save(category: Category)

    suspend fun delete(category: Category)

    suspend fun getAll(): List<Category>
}