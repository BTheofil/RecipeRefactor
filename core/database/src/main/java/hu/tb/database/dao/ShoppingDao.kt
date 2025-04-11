package hu.tb.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.tb.database.entity.ShoppingItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ShoppingItemEntity)

    @Delete
    suspend fun delete(entity: ShoppingItemEntity)

    @Query("SELECT * FROM shoppingitementity")
    fun getAll(): Flow<List<ShoppingItemEntity>>
}