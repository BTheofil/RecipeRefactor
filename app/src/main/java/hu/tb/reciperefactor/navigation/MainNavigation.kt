package hu.tb.reciperefactor.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
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
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import hu.tb.presentation.DepoScreen
import hu.tb.recipe.presentation.create.CreateScreen
import hu.tb.recipe.presentation.details.DetailScreen
import hu.tb.recipe.presentation.main.RecipeScreen
import hu.tb.shopping.presentation.ShopScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    var selectedDestination by remember { mutableStateOf<Destination>(Destination.RecipeScreen()) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets,
                content = {
                    destinations.forEach { destination ->
                        NavigationBarItem(
                            selected = selectedDestination == destination,
                            onClick = {
                                if (selectedDestination == destination) return@NavigationBarItem

                                navController.navigate(destination)

                                selectedDestination = destination
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
                .consumeWindowInsets(innerPadding)
                .padding(innerPadding),
            navController = navController,
            startDestination = selectedDestination
        ) {
            composable<Destination.ShoppingScreen> {
                ShopScreen()
            }

            recipeGraph(navController)

            storageGraph()
        }
    }
}

private fun NavGraphBuilder.storageGraph() {
    navigation<Destination.StorageScreen>(
        startDestination = Storage.Main,
    ) {
        composable<Storage.Main> {
            DepoScreen()
        }
        composable<Storage.Creation> {
            //todo
        }
    }
}

private fun NavGraphBuilder.recipeGraph(controller: NavController) {
    navigation<Destination.RecipeScreen>(
        startDestination = Recipe.Main
    ) {
        composable<Recipe.Main> {
            RecipeScreen(
                createRecipeScreenRequest = { controller.navigate(Recipe.Creation) },
                detailRecipeScreenRequest = { controller.navigate(Recipe.Detail(it)) }
            )
        }

        composable<Recipe.Creation> {
            CreateScreen(
                navigateBack = { controller.popBackStack() }
            )
        }

        composable<Recipe.Detail> {
            DetailScreen()
        }
    }
}