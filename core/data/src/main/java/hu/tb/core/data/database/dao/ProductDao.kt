package hu.tb.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import hu.tb.core.data.database.entity.ProductEntity

@Dao
interface ProductDao: BaseDao<ProductEntity> {

    @Query("SELECT * FROM ProductEntity")
    suspend fun getAll(): List<ProductEntity>
}