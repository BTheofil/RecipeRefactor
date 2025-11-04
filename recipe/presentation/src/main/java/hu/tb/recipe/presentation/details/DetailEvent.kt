package hu.tb.recipe.presentation.details

sealed interface DetailEvent {
    data object RecipeAddedToDepo : DetailEvent
}