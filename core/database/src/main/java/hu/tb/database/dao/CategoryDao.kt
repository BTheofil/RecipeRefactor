package hu.tb.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.tb.database.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<CategoryEntity>)

    @Delete
    suspend fun delete(entity: CategoryEntity)

    @Query("SELECT * FROM categoryentity")
    suspend fun getAll(): List<CategoryEntity>
}