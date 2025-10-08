package hu.tb.recipe.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.product.Product
import hu.tb.core.domain.product.ProductRepository
import hu.tb.core.domain.recipe.RecipeRepository
import hu.tb.core.domain.recipe.details.Availability
import hu.tb.core.domain.recipe.details.IngredientAvailability
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val recipeId: Long = checkNotNull(savedStateHandle["recipeId"])
            _state.update { it.copy(recipe = recipeRepository.getRecipeById(recipeId)) }
        }
    }

    fun checkIngredientsAndMakeIt() {
        if (state.value.recipeIngredientsResult.isEmpty()) {
            checkProductAvailability()
            return
        }

        makeRecipeToProduct()
    }

    private fun makeRecipeToProduct() {
        state.value.recipe?.let {
            viewModelScope.launch {
                it.ingredients.forEach { product ->
                    val productInDepo =
                        productRepository.getProductByNameAndMeasure(product.name, product.measure)

                    if (productInDepo != null && productInDepo.measure.category == product.measure.category) {
                        val depoQuantity = productInDepo.quantity * productInDepo.measure.factor
                        val productQuantity = product.quantity * product.measure.factor

                        val newQuantity =
                            (depoQuantity - productQuantity) / productInDepo.measure.factor
                        productRepository.insert(productInDepo.copy(quantity = newQuantity))
                    }
                }

                val recipeProductInDepo = productRepository.getProductByNameAndMeasure(it.name, Measure.PIECE)
                val newProduct = recipeProductInDepo?.copy(quantity = recipeProductInDepo.quantity + 1.0)
                    ?: Product(name = it.name, quantity = 1.0, measure = Measure.PIECE)
                productRepository.insert(newProduct)

                resetRecipeCheck()
            }
        }
    }

    private fun resetRecipeCheck() {
        _state.update {
            it.copy(
                recipeIngredientsResult = emptyList(),
                isRecipeCookable = false
            )
        }
    }

    private fun checkProductAvailability() {
        state.value.recipe?.ingredients?.forEach { product ->
            viewModelScope.launch {
                val availability = calculateAvailability(product)
                val updatedList = state.value.recipeIngredientsResult.toMutableList().apply {
                    add(IngredientAvailability(product, availability))
                }
                val allEnough = updatedList.none { it.availability == Availability.LESS }

                _state.update { currentState ->
                    currentState.copy(
                        recipeIngredientsResult = updatedList,
                        isRecipeCookable = allEnough
                    )
                }
            }
        }
    }

    private suspend fun calculateAvailability(product: Product): Availability {
        val depo = productRepository.getProductByNameAndMeasure(product.name, product.measure)
            ?: return Availability.UNKNOWN

        if (depo.measure.category != product.measure.category) return Availability.UNKNOWN

        val depoQuantity = depo.quantity * depo.measure.factor
        val productQuantity = product.quantity * product.measure.factor

        return if (depoQuantity >= productQuantity) Availability.HAVE else Availability.LESS
    }
}