package hu.tb.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.tb.core.data.database.entity.ProductEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ProductEntity)

    @Delete
    suspend fun delete(entity: ProductEntity)

    @Query("SELECT * FROM ProductEntity")
    suspend fun getAll(): List<ProductEntity>
}