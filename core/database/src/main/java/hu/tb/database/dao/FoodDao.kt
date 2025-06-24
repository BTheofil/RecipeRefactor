package hu.tb.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.tb.database.entity.FoodEntity

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FoodEntity)

    @Delete
    suspend fun delete(entity: FoodEntity)

    @Query("SELECT * FROM FoodEntity")
    suspend fun getAll(): List<FoodEntity>
}