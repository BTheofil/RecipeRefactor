package hu.tb.reciperefactor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.tb.recipe.presentation.RecipeScreen
import hu.tb.shopping.presentation.ShoppingScreen
import kotlinx.serialization.Serializable

@Serializable
object ShoppingScreen

@Serializable
object RecipeScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ShoppingScreen
    ) {
        composable<ShoppingScreen> {
            ShoppingScreen()
        }

        composable<RecipeScreen> {
            RecipeScreen()
        }
    }
}