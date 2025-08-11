package hu.tb.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import hu.tb.core.data.database.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDao : BaseDao<ProductEntity> {

    @Query("SELECT * FROM productentity WHERE isDraft = 1")
    fun getAll(): Flow<List<ProductEntity>>
}