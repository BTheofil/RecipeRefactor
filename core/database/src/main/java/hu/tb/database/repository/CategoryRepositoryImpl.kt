package hu.tb.database.repository

import hu.tb.core.domain.meal.Category
import hu.tb.core.domain.meal.CategoryRepository
import hu.tb.database.dao.CategoryDao
import hu.tb.database.mapper.toDomain
import hu.tb.database.mapper.toEntity

class CategoryRepositoryImpl(
    private val dao: CategoryDao
): CategoryRepository {
    override suspend fun saveAll(categories: List<Category>) =
        dao.insert(categories.map { it.toEntity() })

    override suspend fun delete(category: Category) =
        dao.delete(category.toEntity())

    override suspend fun getAll(): List<Category> =
        dao.getAll().map { category -> category.toDomain() }
}