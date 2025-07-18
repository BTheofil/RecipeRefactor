package hu.tb.presentation.storage

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StorageViewModel() : ViewModel() {

    private val _state = MutableStateFlow(StorageState())
    val state = _state.asStateFlow()

    fun onAction(action: StorageAction) {
        when (action) {
            else -> {}
        }
    }
}