package hu.tb.reciperefactor.navigation

import hu.tb.reciperefactor.R
import kotlinx.serialization.Serializable

sealed interface Destination {
    val route: String
    val icon: Int

    @Serializable
    data class ShoppingScreen(
        override val route: String = "Shopping List",
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

val destinations =
    listOf(
        Destination.ShoppingScreen(),
        Destination.RecipeScreen(),
        Destination.StorageScreen()
    )

sealed interface Storage {
    @Serializable object Main
    @Serializable object Creation
}

sealed interface Recipe {
    @Serializable object Main
    @Serializable object Creation
}