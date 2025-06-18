package hu.tb.reciperefactor

import android.app.Application
import hu.tb.core.data.di.dataModule
import hu.tb.database.di.databaseModule
import hu.tb.di.storageModule
import hu.tb.recipe.presentation.di.recipeModule
import hu.tb.shopping.presentation.di.shoppingModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RecipeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@RecipeApp)
            modules(
                dataModule,
                databaseModule,
                shoppingModule,
                recipeModule,
                storageModule
            )
        }
    }
}