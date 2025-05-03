package hu.tb.reciperefactor.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.tb.recipe.presentation.RecipeScreen
import hu.tb.reciperefactor.R
import hu.tb.shopping.presentation.ShoppingScreen
import kotlinx.serialization.Serializable

sealed interface Destination {
    val route: String
    val icon: Int

    @Serializable
    data class ShoppingScreen(
        override val route: String = "ShoppingList",
        override val icon: Int = R.drawable.outline_shopping_cart_24
    ) : Destination

    @Serializable
    data class RecipeScreen(
        override val route: String = "Recipe",
        override val icon: Int = R.drawable.outline_book_24
    ) : Destination

    @Serializable
    data class StorageScreen(
        override val route: String = "Storage",
        override val icon: Int = R.drawable.outline_storage_24
    ) : Destination
}

private val destinations =
    listOf<Destination>(
        Destination.ShoppingScreen(),
        Destination.RecipeScreen(),
        Destination.StorageScreen()
    )

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    var selectedDestination by remember { mutableStateOf<Destination>(Destination.ShoppingScreen()) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            NavigationBar(
                content = {
                    destinations.forEach { destination ->
                        NavigationBarItem(
                            selected = selectedDestination == destination,
                            onClick = {
                                selectedDestination = destination
                                navController.navigate(selectedDestination)
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(destination.icon),
                                    tint = if (selectedDestination == destination)
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    else
                                        MaterialTheme.colorScheme.onSurface,
                                    contentDescription = "navigation bar icon"
                                )
                            },
                            label = {
                                Text(
                                    text = destination.route,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (selectedDestination == destination)
                                        MaterialTheme.colorScheme.onSurface
                                    else
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .padding(innerPadding),
            navController = navController,
            startDestination = Destination.ShoppingScreen()
        ) {
            composable<Destination.ShoppingScreen> {
                ShoppingScreen()
            }

            composable<Destination.RecipeScreen> {
                RecipeScreen()
            }

            composable<Destination.StorageScreen> {
                Box(content = {
                    Text("storage screen")
                })
            }
        }
    }

}