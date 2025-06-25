package hu.tb.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import hu.tb.presentation.create.CreationScreen
import hu.tb.presentation.storage.StorageScreen
import kotlinx.serialization.Serializable

@Serializable
object Main
@Serializable
object Creation

inline fun <reified T : Any> NavGraphBuilder.storageGraph(controller: NavController) {
    navigation<T>(
        startDestination = Main,
    ) {
        composable<Main> {
            StorageScreen(onCreationRequested = { controller.navigate(Creation) })
        }
        composable<Creation> {
            CreationScreen()
        }
    }
}