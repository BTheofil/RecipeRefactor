package hu.tb.recipe.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.product.Product
import hu.tb.core.domain.product.ProductRepository
import hu.tb.core.domain.recipe.Recipe
import hu.tb.core.domain.recipe.RecipeRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    val recipe = mutableStateOf<Recipe?>(null)

    init {
        viewModelScope.launch {
            val recipeId: Long = checkNotNull(savedStateHandle["recipeId"])
            recipe.value = recipeRepository.getRecipeById(recipeId)
        }
    }

    fun makeRecipeToProduct() {
        recipe.value?.let {
            viewModelScope.launch {
                it.ingredients.forEach { product ->
                    val productInDepo =
                        productRepository.getProductByNameAndMeasure(product.name, product.measure)

                    if (productInDepo != null && productInDepo.measure.category == product.measure.category) {
                        val depoQuantity = productInDepo.quantity * productInDepo.measure.factor
                        val productQuantity = product.quantity * product.measure.factor

                        val newQuantity = depoQuantity - productQuantity
                        productRepository.insert(productInDepo.copy(quantity = newQuantity))
                    }
                }

                val newProduct = Product(name = it.name, quantity = 1.0, measure = Measure.PIECE)
                productRepository.insert(newProduct)
            }
        }
    }
}