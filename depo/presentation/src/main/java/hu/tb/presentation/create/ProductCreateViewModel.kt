package hu.tb.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.product.Product
import hu.tb.core.domain.product.ProductRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProductCreateViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _event = Channel<Long?>()
    val event = _event.receiveAsFlow()

    fun addNewProduct(new: Product) {
        viewModelScope.launch {
            val newId = productRepository.insert(new)
            _event.send(newId)
        }
    }
}