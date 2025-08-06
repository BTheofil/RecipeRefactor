package hu.tb.recipe.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.recipe.Recipe
import hu.tb.core.domain.recipe.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreationState())
    val state = _state.asStateFlow()

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

            is CreateAction.StepsAction.OnAddStep -> _state.update {
                it.copy(
                    steps = state.value.steps.toMutableList() + ""
                )
            }

            is CreateAction.StepsAction.OnDone -> {
                viewModelScope.launch {
                    val recipeId = recipeRepository.save(
                        recipe = Recipe(
                            name = state.value.recipeName,
                            ingredients = state.value.ingredients,
                            howToMakeSteps = state.value.steps
                        )
                    )
                    println(recipeId)
                }
            }

            else -> {}
        }
    }
}