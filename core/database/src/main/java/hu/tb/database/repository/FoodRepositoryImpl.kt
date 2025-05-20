package hu.tb.database.repository

import hu.tb.core.domain.meal.Category
import hu.tb.core.domain.meal.FoodRepository
import hu.tb.database.dao.FoodDao
import hu.tb.database.mapper.toDomain
import hu.tb.database.mapper.toEntity

class FoodRepositoryImpl(
    private val dao: FoodDao
) : FoodRepository {

    override suspend fun save(category: Category) =
        dao.insert(category.toEntity())

    override suspend fun delete(category: Category) =
        dao.delete(category.toEntity())

    override suspend fun getAll(): List<Category> =
        dao.getAll().map { items ->
            items.toDomain()
        }
}