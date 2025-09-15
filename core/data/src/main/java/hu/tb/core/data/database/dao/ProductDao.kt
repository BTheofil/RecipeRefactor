package hu.tb.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import hu.tb.core.data.database.entity.ProductEntity
import hu.tb.core.domain.product.Measure
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao: BaseDao<ProductEntity> {

    @Query("SELECT * FROM ProductEntity WHERE recipeIdConnection IS NULL AND isChecked IS NULL")
    suspend fun getAllCurrent(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE recipeIdConnection IS NULL AND isChecked IS NULL")
    fun getAllFlow(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM ProductEntity " +
            "WHERE recipeIdConnection IS NULL AND isChecked IS NULL " +
            "AND name = :name AND measure =:measure"
    )
    suspend fun getProductByNameAndMeasure(name: String, measure: Measure): ProductEntity?
}