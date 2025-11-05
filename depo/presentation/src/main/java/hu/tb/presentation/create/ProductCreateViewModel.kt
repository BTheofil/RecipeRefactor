package hu.tb.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.product.Product
import hu.tb.core.domain.product.ProductRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductCreateViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductCreateState())
    val state = _state.asStateFlow()

    private val _event = Channel<Long?>()
    val event = _event.receiveAsFlow()

    fun addNewProduct(new: Product) {
        viewModelScope.launch {
            checkValidInput(new)

            if (!_state.value.isNameError && !_state.value.isQuantityError) {
                val productAlreadyIn = checkIfAlreadyInserted(new.name, new.measure)

                val resultProduct = if (productAlreadyIn != null) {
                    val newProductUnit = new.quantity * new.measure.factor
                    val alreadyProductUnit =
                        productAlreadyIn.quantity * productAlreadyIn.measure.factor
                    productRepository.insert(productAlreadyIn.copy(quantity = (newProductUnit + alreadyProductUnit) / productAlreadyIn.measure.factor))
                } else {
                    productRepository.insert(new)
                }

                _event.send(resultProduct)
            }
        }
    }

    private fun checkValidInput(givenValues: Product) {
        _state.update {
            it.copy(
                isNameError = givenValues.name.isBlank(),
                isQuantityError = givenValues.quantity <= 0
            )
        }
    }

    private suspend fun checkIfAlreadyInserted(
        newProductName: String,
        newProductMeasure: Measure
    ): Product? {
        val checkResult = productRepository.getProductByName(newProductName)
        if (checkResult.isEmpty()) return null

        return checkResult.find { it.name == newProductName && it.measure.category == newProductMeasure.category }
    }
}