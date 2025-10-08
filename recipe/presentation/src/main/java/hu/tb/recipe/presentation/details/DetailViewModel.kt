package hu.tb.recipe.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.product.Product
import hu.tb.core.domain.product.ProductRepository
import hu.tb.core.domain.recipe.Recipe
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
                updateProductStockForRecipe(it)
                insertOrUpdateFinalProduct(it.name)
                resetRecipeCheck()
            }
        }
    }

    private suspend fun updateProductStockForRecipe(recipe: Recipe) {
        recipe.ingredients.forEach { ingredient ->
            val productInDepo = productRepository.getProductByNameAndMeasure(
                ingredient.name, ingredient.measure
            )

            if (productInDepo != null && productInDepo.measure.category == ingredient.measure.category) {
                val depoQuantity = productInDepo.quantity * productInDepo.measure.factor
                val requiredQuantity = ingredient.quantity * ingredient.measure.factor

                val updatedQuantity =
                    (depoQuantity - requiredQuantity) / productInDepo.measure.factor
                productRepository.insert(productInDepo.copy(quantity = updatedQuantity))
            }
        }
    }

    private suspend fun insertOrUpdateFinalProduct(recipeName: String) {
        val existingProduct =
            productRepository.getProductByNameAndMeasure(recipeName, Measure.PIECE)

        val updatedProduct = existingProduct?.copy(
            quantity = existingProduct.quantity + 1.0
        ) ?: Product(name = recipeName, quantity = 1.0, measure = Measure.PIECE)

        productRepository.insert(updatedProduct)
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
        state.value.recipe?.let { recipe ->
            viewModelScope.launch {
                val updatedList = recipe.ingredients.map { ingredient ->
                    val availability = calculateAvailability(ingredient)
                    IngredientAvailability(ingredient, availability)
                }

                val allEnough = updatedList.none { it.availability == Availability.LESS }

                _state.update {
                    it.copy(
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