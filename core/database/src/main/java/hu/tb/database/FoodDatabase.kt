package hu.tb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.tb.database.dao.FoodDao
import hu.tb.database.entity.CategoryEntity

@Database(
    entities = [CategoryEntity::class],
    version = 1
)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}