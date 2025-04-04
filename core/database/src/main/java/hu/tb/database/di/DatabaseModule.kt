package hu.tb.database.di

import androidx.room.Room
import hu.tb.database.ShoppingDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            ShoppingDatabase::class.java,
            "shopping.db"
        ).build()
    }

    single { get<ShoppingDatabase>().shoppingDao() }
}