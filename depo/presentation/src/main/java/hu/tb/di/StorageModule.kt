package hu.tb.di

import hu.tb.presentation.storage.StorageViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val storageModule = module {
    viewModelOf(::StorageViewModel)
}