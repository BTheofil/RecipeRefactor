package hu.tb.di

import hu.tb.presentation.storage.DepoViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val storageModule = module {
    viewModelOf(::DepoViewModel)
}