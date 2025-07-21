package hu.tb.core.data.di

import androidx.room.Room
import hu.tb.core.data.database.ApplicationDatabase
import hu.tb.core.data.database.repository.ProductRepositoryImpl
import hu.tb.core.data.database.repository.RecipeRepositoryImpl
import hu.tb.core.data.database.repository.ShopItemRepositoryImpl
import hu.tb.core.data.network.KtorClient
import hu.tb.core.domain.product.ProductRepository
import hu.tb.core.domain.recipe.RecipeRepository
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
            ApplicationDatabase::class.java,
            "application.db"
        ).build()
    }

    single { get<ApplicationDatabase>().shopDao() }
    single { get<ApplicationDatabase>().productDao() }
    single { get<ApplicationDatabase>().recipeDao() }

    singleOf(::ShopItemRepositoryImpl).bind<ShopItemRepository>()
    singleOf(::ProductRepositoryImpl).bind<ProductRepository>()
    singleOf(::RecipeRepositoryImpl).bind<RecipeRepository>()
}