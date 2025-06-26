package hu.tb.presentation.storage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.meal.CategoryRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StorageViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StorageState())
    val state = _state.asStateFlow()

    private val _event = Channel<StorageEvent>()
    val event = _event.receiveAsFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    categories = categoryRepository.getAll()
                )
            }
        }
    }

    fun onAction(action: StorageAction) {
        when (action) {
            is StorageAction.OnAddFoodClick -> viewModelScope.launch {
                _event.send(StorageEvent.NavigateToCreation)
            }

            else -> {}
        }
    }
}