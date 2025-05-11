package hu.tb.core.data.di

import hu.tb.core.data.network.KtorClient
import hu.tb.core.data.network.category.CategoryDataSourceImpl
import hu.tb.core.data.network.meal.MealDataSourceImpl
import hu.tb.core.domain.category.CategoryDataSource
import hu.tb.core.domain.meal.MealDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single {
        KtorClient.build()
    }

    singleOf(::MealDataSourceImpl).bind<MealDataSource>()
    singleOf(::CategoryDataSourceImpl).bind<CategoryDataSource>()
}