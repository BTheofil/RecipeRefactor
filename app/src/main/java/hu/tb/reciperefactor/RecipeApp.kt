package hu.tb.reciperefactor

import android.app.Application
import hu.tb.database.di.databaseModule
import hu.tb.presentation.di.shoppingModule
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
                databaseModule,
                shoppingModule
            )
        }
    }
}