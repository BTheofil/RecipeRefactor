package hu.tb.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.meal.Food
import hu.tb.core.domain.meal.FoodRepository
import kotlinx.coroutines.launch

class CreationViewModel(
    private val foodRepository: FoodRepository
) : ViewModel() {

    fun onAction(action: CreationAction) {
        when (action) {
            is CreationAction.OnDoneClick -> viewModelScope.launch {

                val food = action.run {
                    Food(
                        name = productText,
                        category = selectedCategory,
                        quantity = quantity,
                        measure = measure
                    )
                }

                foodRepository.insert(food)
            }
        }
    }
}