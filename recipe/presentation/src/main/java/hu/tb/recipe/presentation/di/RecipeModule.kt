package hu.tb.recipe.presentation.di

import hu.tb.recipe.presentation.RecipeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val recipeModule = module {
    viewModelOf(::RecipeViewModel)
}