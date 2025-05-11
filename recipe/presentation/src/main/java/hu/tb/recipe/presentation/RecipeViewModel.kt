package hu.tb.recipe.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.category.CategoryDataSource
import hu.tb.core.domain.meal.MealDataSource
import hu.tb.core.domain.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val mealDataSource: MealDataSource,
    private val categoryDataSource: CategoryDataSource,
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getMeals()
            getCategories()
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

    private suspend fun getMeals() {
        _state.update { it.copy(isMealsLoading = true) }
        when (val result = mealDataSource.getRandomMeal()) {
            is Result.Error -> _state.update {
                it.copy(isErrorOccurred = true)
            }

            is Result.Success -> _state.update {
                it.copy(meals = result.data)
            }
        }
        _state.update { it.copy(isMealsLoading = false) }
    }

    private suspend fun getCategories() {
        _state.update { it.copy(isCategoriesLoading = true) }
        when (val result = categoryDataSource.getCategories()) {
            is Result.Error -> _state.update {
                it.copy(isErrorOccurred = true)
            }

            is Result.Success -> _state.update {
                it.copy(categories = result.data)
            }
        }
        _state.update { it.copy(isCategoriesLoading = false) }
    }
}