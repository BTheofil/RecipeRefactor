package hu.tb.recipe.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.meal.MealDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val mealDataSource: MealDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val result = mealDataSource.getRandomMeal()
        }
    }

    fun onAction(action: RecipeAction) {

    }
}