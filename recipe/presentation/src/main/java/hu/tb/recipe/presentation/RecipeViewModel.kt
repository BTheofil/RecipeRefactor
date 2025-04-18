package hu.tb.recipe.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.meal.MealDataSource
import hu.tb.core.domain.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val mealDataSource: MealDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(isMealsLoading = true) }
            when (val result = mealDataSource.getRandomMeal()) {
                is Result.Error -> _state.update {
                    it.copy(isErrorOccurred = true)
                }

                is Result.Success -> _state.update {
                    it.copy(
                        meals = result.data
                    )
                }
            }
            _state.update { it.copy(isMealsLoading = false) }
        }
    }

    fun onAction(action: RecipeAction) =
        when (action) {
            is RecipeAction.OnSearch -> {}
            is RecipeAction.OnSearchTextChange ->
                _state.update {
                    it.copy(
                        searchField = action.text
                    )
                }
        }
}