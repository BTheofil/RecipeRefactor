package hu.tb.database.repository

import hu.tb.core.domain.meal.Food
import hu.tb.core.domain.meal.FoodRepository
import hu.tb.database.dao.FoodDao
import hu.tb.database.entity.toDomain
import hu.tb.database.entity.toEntity

class FoodRepositoryImpl(
    private val dao: FoodDao
) : FoodRepository {

    override suspend fun insert(food: Food) =
        dao.insert(food.toEntity())

    override suspend fun delete(food: Food) =
        dao.delete(food.toEntity())

    override suspend fun getAll(): List<Food> =
        dao.getAll().map { food -> food.toDomain() }
}