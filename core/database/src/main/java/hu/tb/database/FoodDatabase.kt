package hu.tb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.tb.database.dao.CategoryDao
import hu.tb.database.dao.FoodDao
import hu.tb.database.entity.CategoryEntity
import hu.tb.database.entity.FoodMeasureConverter
import hu.tb.database.entity.FoodEntity

@Database(
    entities = [
        FoodEntity::class,
        CategoryEntity::class
    ],
    version = 1
)
@TypeConverters(
    FoodMeasureConverter::class
)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun categoryDao(): CategoryDao
}