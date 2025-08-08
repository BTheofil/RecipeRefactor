package hu.tb.recipe.presentation.create

sealed interface CreationEvent {
    data object RecipeSaved: CreationEvent
}