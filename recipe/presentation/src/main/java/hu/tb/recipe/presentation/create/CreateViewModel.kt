package hu.tb.recipe.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.product.ProductRepository
import hu.tb.core.domain.recipe.Recipe
import hu.tb.core.domain.recipe.RecipeRepository
import hu.tb.core.domain.step.Step
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateViewModel(
    private val recipeRepository: RecipeRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreationState())
    val state = _state.asStateFlow()

    private val _event = Channel<CreationEvent>()
    val event = _event.receiveAsFlow()

    init {
        viewModelScope.launch {
            val currentProducts = productRepository.getAllCurrent()
            _state.update {
                it.copy(
                    productsInDepo = currentProducts
                )
            }
        }
    }

    fun onAction(action: CreateAction) {
        when (action) {
            is CreateAction.IngredientsAction.OnAddIngredients -> _state.update {
                it.copy(
                    ingredients = state.value.ingredients.toMutableList() + action.product
                )
            }

            is CreateAction.IngredientsAction.OnRemoveIngredient -> {
                val temp = state.value.ingredients.toMutableList()
                temp.removeAt(action.productIndex)

                _state.update {
                    it.copy(
                        ingredients = temp
                    )
                }
            }

            is CreateAction.StarterAction.RecipeNameChange -> _state.update {
                it.copy(
                    recipeName = action.name
                )
            }

            is CreateAction.StepsAction.StepFieldChange -> _state.update {
                it.copy(
                    steps = state.value.steps.toMutableList().apply {
                        this[action.index] = action.text
                    }
                )
            }

            is CreateAction.StepsAction.AddStepField -> _state.update {
                it.copy(
                    steps = state.value.steps.toMutableList() + ""
                )
            }

            is CreateAction.StepsAction.FinishSteps -> {
                viewModelScope.launch {
                    val recipeId = recipeRepository.save(
                        recipe = Recipe(
                            name = state.value.recipeName,
                            ingredients = state.value.ingredients,
                            howToMakeSteps = state.value.steps.map { Step(description = it) }
                        )
                    )
                    if (recipeId > -1) {
                        _event.send(CreationEvent.RecipeSaved)
                    }
                }
            }

            else -> {}
        }
    }
}