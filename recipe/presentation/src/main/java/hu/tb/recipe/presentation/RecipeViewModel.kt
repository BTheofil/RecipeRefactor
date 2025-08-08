package hu.tb.recipe.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RecipeViewModel() : ViewModel() {

    private val _state = MutableStateFlow(RecipeState())
    val state = _state.asStateFlow()

    fun onAction(action: RecipeAction) =
        when (action) {
            is RecipeAction.OnSearch -> {}
            is RecipeAction.OnSearchTextChange ->
                _state.update {
                    it.copy(
                        searchField = action.text
                    )
                }
            else -> {}

        }
}