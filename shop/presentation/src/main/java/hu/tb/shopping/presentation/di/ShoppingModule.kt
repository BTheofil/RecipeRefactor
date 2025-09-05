package hu.tb.shopping.presentation.di

import hu.tb.shopping.presentation.ShopViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val shoppingModule = module {
    viewModelOf(::ShopViewModel)
}