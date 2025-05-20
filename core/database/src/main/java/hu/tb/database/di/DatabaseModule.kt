package hu.tb.database.di

import androidx.room.Room
import hu.tb.core.domain.meal.FoodRepository
import hu.tb.database.ShoppingDatabase
import hu.tb.database.repository.ShoppingRepositoryImpl
import hu.tb.core.domain.shopping.ShoppingRepository
import hu.tb.database.FoodDatabase
import hu.tb.database.repository.FoodRepositoryImpl
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

    single {
        Room.databaseBuilder(
            androidApplication(),
            FoodDatabase::class.java,
            "food.db"
        ).build()
    }

    single { get<FoodDatabase>().foodDao() }

    singleOf(::FoodRepositoryImpl).bind<FoodRepository>()
}