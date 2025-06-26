package hu.tb.reciperefactor.navigation

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
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import hu.tb.presentation.create.CreationScreen
import hu.tb.presentation.storage.StorageScreen
import hu.tb.recipe.presentation.RecipeScreen
import hu.tb.shopping.presentation.ShoppingScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    var selectedDestination by remember { mutableStateOf<Destination>(Destination.RecipeScreen()) }

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
                .padding(innerPadding),
            navController = navController,
            startDestination = selectedDestination
        ) {
            composable<Destination.ShoppingScreen> {
                ShoppingScreen()
            }

            composable<Destination.RecipeScreen> {
                RecipeScreen()
            }

            storageGraph(navController)
        }
    }
}

private fun NavGraphBuilder.storageGraph(controller: NavController) {
    navigation<Destination.StorageScreen>(
        startDestination = Storage.Main,
    ) {
        composable<Storage.Main> {
            StorageScreen(onCreationRequested = { controller.navigate(Storage.Creation) })
        }
        composable<Storage.Creation> {
            CreationScreen(
                finishCreation = { controller.popBackStack() }
            )
        }
    }
}