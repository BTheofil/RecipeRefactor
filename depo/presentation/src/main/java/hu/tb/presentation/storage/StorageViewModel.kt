package hu.tb.presentation.storage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.product.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StorageViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StorageState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            productRepository.getAllFlow().collect { products ->
                _state.update {
                    it.copy(products = products)
                }
            }
        }
    }

    fun onAction(action: StorageAction) {
        when (action) {
            else -> {}
        }
    }
}