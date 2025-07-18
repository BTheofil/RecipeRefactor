package hu.tb.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.product.Product
import hu.tb.core.domain.product.ProductRepository
import kotlinx.coroutines.launch

class CreationViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    fun onAction(action: CreationAction) {
        when (action) {
            is CreationAction.OnDoneClick -> viewModelScope.launch {

                val product = action.run {
                    Product(
                        name = productText,
                        quantity = quantity,
                        measure = measure
                    )
                }

                productRepository.insert(product)
            }
        }
    }
}