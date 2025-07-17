package hu.tb.core.data.di

import androidx.room.Room
import hu.tb.core.data.database.ProductDatabase
import hu.tb.core.data.database.ShoppingDatabase
import hu.tb.core.data.database.repository.ProductRepositoryImpl
import hu.tb.core.data.database.repository.ShopItemRepositoryImpl
import hu.tb.core.data.network.KtorClient
import hu.tb.core.domain.product.ProductRepository
import hu.tb.core.domain.shop.ShopItemRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single {
        KtorClient.build()
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            ShoppingDatabase::class.java,
            "shopping.db"
        ).build()
    }

    single { get<ShoppingDatabase>().shoppingDao() }

    singleOf(::ShopItemRepositoryImpl).bind<ShopItemRepository>()

    single {
        Room.databaseBuilder(
            androidApplication(),
            ProductDatabase::class.java,
            "food.db"
        ).build()
    }

    single { get<ProductDatabase>().foodDao() }

    singleOf(::ProductRepositoryImpl).bind<ProductRepository>()
}