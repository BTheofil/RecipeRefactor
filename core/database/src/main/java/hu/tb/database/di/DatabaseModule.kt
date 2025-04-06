package hu.tb.database.di

import androidx.room.Room
import hu.tb.database.ShoppingDatabase
import hu.tb.database.repository.ShoppingRepositoryImpl
import hu.tb.domain.ShoppingRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
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

    singleOf(::ShoppingRepositoryImpl).bind<ShoppingRepository>()
}