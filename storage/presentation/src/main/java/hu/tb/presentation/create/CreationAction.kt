package hu.tb.presentation.create

sealed interface CreationAction {
    data class OnDoneClick(val productText: String): CreationAction
}