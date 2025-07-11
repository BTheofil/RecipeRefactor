package hu.tb.presentation.storage

sealed interface StorageEvent {
    object NavigateToCreation: StorageEvent
}