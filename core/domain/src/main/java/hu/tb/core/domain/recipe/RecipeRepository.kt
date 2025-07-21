package hu.tb.core.domain.recipe

import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun save(recipe: Recipe): Long

    suspend fun delete(recipe: Recipe)

    fun getAll(): Flow<List<Recipe>>
}