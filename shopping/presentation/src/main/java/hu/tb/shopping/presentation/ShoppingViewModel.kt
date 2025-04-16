package hu.tb.shopping.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.domain.ShoppingItem
import hu.tb.domain.ShoppingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShoppingViewModel(
    private val repository: ShoppingRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ShoppingState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllItem().collect { repoItems ->
                val unchecked = repoItems.filter { it.isChecked == false }
                val checked = repoItems.filter { it.isChecked == true }
                _state.update {
                    ShoppingState(
                        uncheckedItems = unchecked,
                        checkedItems = checked
                    )
                }
            }
        }
    }

    fun onAction(action: ShoppingAction) {
        when (action) {
            ShoppingAction.OnClearButtonClick -> deleteAllItems()
            is ShoppingAction.OnItemCheckChange -> storeItem(action.item)
            is ShoppingAction.OnCreateDialogSaveButtonClick -> storeItem(action.newItem)
            is ShoppingAction.OnEditItemChange -> storeItem(action.item)
            is ShoppingAction.OnDeleteSingleButtonClick -> deleteItem(action.item)
        }
    }

    private fun deleteAllItems() = viewModelScope.launch {
        state.value.checkedItems.forEach { item ->
            repository.deleteItem(item)
        }
        state.value.uncheckedItems.forEach { item ->
            repository.deleteItem(item)
        }
    }


    private fun storeItem(item: ShoppingItem) = viewModelScope.launch {
        repository.saveItem(
            item
        )
    }

    private fun deleteItem(item: ShoppingItem) = viewModelScope.launch {
        repository.deleteItem(item)
    }
}