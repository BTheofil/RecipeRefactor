package hu.tb.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import hu.tb.core.data.database.entity.ShopItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDao : BaseDao<ShopItemEntity> {

    @Query("SELECT * FROM shopitementity")
    fun getAll(): Flow<List<ShopItemEntity>>
}