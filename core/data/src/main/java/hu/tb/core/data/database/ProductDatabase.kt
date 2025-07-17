package hu.tb.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.tb.core.data.database.dao.ProductDao
import hu.tb.core.data.database.entity.ProductEntity
import hu.tb.core.data.database.entity.ProductMeasureConverter

@Database(
    entities = [
        ProductEntity::class,
    ],
    version = 1
)
@TypeConverters(
    ProductMeasureConverter::class
)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun foodDao(): ProductDao
}