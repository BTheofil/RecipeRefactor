package hu.tb.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.tb.core.data.database.dao.ShopDao
import hu.tb.core.data.database.entity.ShopItemEntity

@Database(entities = [ShopItemEntity::class], version = 1)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun shoppingDao(): ShopDao
}