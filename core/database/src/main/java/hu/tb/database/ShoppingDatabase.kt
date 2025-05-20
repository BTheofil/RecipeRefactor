package hu.tb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.tb.database.dao.ShoppingDao
import hu.tb.database.entity.ShoppingItemEntity

@Database(entities = [ShoppingItemEntity::class], version = 1)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun shoppingDao(): ShoppingDao
}