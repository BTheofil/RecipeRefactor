package hu.tb.recipe.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.recipe.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            recipeRepository.getAllFlow().collect { recipes ->
                _state.update {
                    it.copy(
                        recipes = recipes
                    )
                }
            }
        }
    }
}