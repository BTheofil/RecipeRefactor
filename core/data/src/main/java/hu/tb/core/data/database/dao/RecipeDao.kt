package hu.tb.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import hu.tb.core.data.database.entity.ProductEntity
import hu.tb.core.data.database.entity.RecipeCompleteEntity
import hu.tb.core.data.database.entity.RecipeEntity
import hu.tb.core.data.database.entity.StepEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao: BaseDao<RecipeEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeStep(step: StepEntity)

    @Transaction
    @Query("SELECT * FROM recipeentity")
    fun getAll(): Flow<List<RecipeCompleteEntity>>
}