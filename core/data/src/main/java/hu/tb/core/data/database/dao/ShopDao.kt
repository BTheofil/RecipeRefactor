package hu.tb.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.tb.core.data.database.entity.ShopItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ShopItemEntity)

    @Delete
    suspend fun delete(entity: ShopItemEntity)

    @Query("SELECT * FROM shopitementity")
    fun getAll(): Flow<List<ShopItemEntity>>
}