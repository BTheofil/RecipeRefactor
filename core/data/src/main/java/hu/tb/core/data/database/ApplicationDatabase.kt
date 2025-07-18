package hu.tb.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.tb.core.data.database.dao.ProductDao
import hu.tb.core.data.database.dao.ShopDao
import hu.tb.core.data.database.entity.ProductEntity
import hu.tb.core.data.database.entity.ProductMeasureConverter
import hu.tb.core.data.database.entity.ShopItemEntity

@Database(
    entities = [
        ShopItemEntity::class,
        ProductEntity::class
    ],
    version = 1
)
@TypeConverters(ProductMeasureConverter::class)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun shopDao(): ShopDao
    abstract fun productDao(): ProductDao
}