package hu.tb.core.data.database.repository

import hu.tb.core.data.database.dao.RecipeDao
import hu.tb.core.data.database.entity.StepEntity
import hu.tb.core.data.database.entity.toDomain
import hu.tb.core.data.database.entity.toEntity
import hu.tb.core.domain.recipe.Recipe
import hu.tb.core.domain.recipe.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipeRepositoryImpl(
    private val dao: RecipeDao
) : RecipeRepository {

    override suspend fun save(recipe: Recipe): Long {
        val recipeId = dao.insert(recipe.toEntity())
        recipe.ingredients.forEach {
            dao.insertRecipeProduct(it.toEntity(recipeId))
        }
        recipe.howToMakeSteps.forEach {
            dao.insertRecipeStep(
                StepEntity(
                    description = it.description,
                    recipeIdConnection = recipeId
                )
            )
        }

        return recipeId
    }

    override suspend fun delete(recipe: Recipe) =
        dao.delete(recipe.toEntity())

    override fun getAllFlow(): Flow<List<Recipe>> =
        dao.getAll().map { entities ->
            entities.map { (recipeEntity, productList, stepList) ->
                Recipe(
                    id = recipeEntity.recipeId,
                    name = recipeEntity.name,
                    ingredients = productList.map { it.toDomain() },
                    howToMakeSteps = stepList.map { it.toDomain() }
                )
            }
        }

    override suspend fun getRecipeById(id: Long): Recipe =
        dao.getRecipeById(id).run {
            Recipe(
                id = recipeEntity.recipeId,
                name = recipeEntity.name,
                ingredients = productEntity.map { it.toDomain() },
                howToMakeSteps = howToMakeSteps.map { it.toDomain() }
            )
        }
}