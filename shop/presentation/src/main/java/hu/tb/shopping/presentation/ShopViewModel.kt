package hu.tb.shopping.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.shop.ShopItem
import hu.tb.core.domain.shop.ShopItemRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShopViewModel(
    private val repository: ShopItemRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ShopState())
    val state = _state.asStateFlow()

    private val _event = Channel<ShopEvent>()
    val event = _event.receiveAsFlow()

    init {
        viewModelScope.launch {
            repository.getAllItem().collect { repoItems ->
                val unchecked = repoItems.filter { !it.isChecked }
                val checked = repoItems.filter { it.isChecked }
                _state.update {
                    ShopState(
                        uncheckedItems = unchecked,
                        checkedItems = checked
                    )
                }
            }
        }
    }

    fun onAction(action: ShopAction) {
        when (action) {
            ShopAction.DeleteAllItems -> deleteAllItems()
            is ShopAction.ShopItemChange -> saveItemChanges(action.shopItem)
            is ShopAction.DeleteItem -> deleteItem(action.item)
            ShopAction.AddAllItemsToStorage -> addShoppingItemsToDepo()
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

    private fun saveItemChanges(item: ShopItem) = viewModelScope.launch {
        repository.saveItem(item)
        checkShoppingIsFinished()
    }

    private fun deleteItem(item: ShopItem) = viewModelScope.launch {
        repository.deleteItem(item)
    }

    private fun addShoppingItemsToDepo() = viewModelScope.launch {
        repository.addShoppingItemsToDepo(state.value.checkedItems)
        deleteAllItems()
    }

    private suspend fun checkShoppingIsFinished() {
        val currentItems = repository.getAllItem().first()
        val completed = currentItems.any { it.isChecked }
        val uncompleted = currentItems.none { !it.isChecked }
        if (uncompleted && completed) {
            _event.send(ShopEvent.ShowShopFinishedDialog)
        }
    }
}