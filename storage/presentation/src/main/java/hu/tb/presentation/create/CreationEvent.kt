package hu.tb.presentation.create

sealed interface CreationEvent {
    object ProductInserted: CreationEvent
}