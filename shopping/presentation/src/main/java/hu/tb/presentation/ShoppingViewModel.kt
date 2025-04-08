package hu.tb.presentation

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

    private val _items = MutableStateFlow(emptyList<ShoppingItem>())
    val items = _items.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllItem().collect { repoItems ->
                _items.update {
                    repoItems
                }
            }
        }
    }

    fun onAction(action: ShoppingAction) {
        when (action) {
            ShoppingAction.OnClearButtonClick -> deleteAllItems()
            is ShoppingAction.OnItemCheckChange -> changeItemCheck(action.item, action.change)
            is ShoppingAction.OnCreateDialogSaveButtonClick -> saveNewItem(action.newItem)

        }
    }

    private fun deleteAllItems() {
        viewModelScope.launch {
            items.value.forEach { item ->
                repository.deleteItem(item)
            }
        }
    }

    private fun changeItemCheck(item: ShoppingItem, change: Boolean) {
        viewModelScope.launch {
            repository.saveItem(
                ShoppingItem(
                    id = item.id,
                    name = item.name,
                    isChecked = change
                )
            )
        }
    }

    private fun saveNewItem(newItem: String) {
        viewModelScope.launch {
            repository.saveItem(
                ShoppingItem(
                    name = newItem
                )
            )
        }
    }
}