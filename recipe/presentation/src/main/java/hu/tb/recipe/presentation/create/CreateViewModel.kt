package hu.tb.recipe.presentation.create

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateViewModel : ViewModel() {

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

            else -> {}
        }
    }
}