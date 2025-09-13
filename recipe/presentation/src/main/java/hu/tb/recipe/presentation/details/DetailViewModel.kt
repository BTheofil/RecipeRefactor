package hu.tb.recipe.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.recipe.Recipe
import hu.tb.core.domain.recipe.RecipeRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    recipeRepository: RecipeRepository,
) : ViewModel() {

    val recipe = mutableStateOf<Recipe?>(null)

    init {
        viewModelScope.launch {
            val recipeId: Long = checkNotNull(savedStateHandle["recipeId"])
            recipe.value = recipeRepository.getRecipeById(recipeId)
        }
    }
}