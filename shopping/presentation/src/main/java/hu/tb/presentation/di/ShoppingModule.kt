package hu.tb.presentation.di

import hu.tb.presentation.ShoppingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val shoppingModule = module {
    viewModelOf(::ShoppingViewModel)
}