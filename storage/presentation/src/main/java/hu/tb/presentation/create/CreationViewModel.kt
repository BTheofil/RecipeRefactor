package hu.tb.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.meal.Category
import hu.tb.core.domain.meal.Food
import hu.tb.core.domain.meal.FoodRepository
import kotlinx.coroutines.launch

class CreationViewModel(
    private val foodRepository: FoodRepository
) : ViewModel() {

    fun onAction(action: CreationAction) {
        when (action) {
            is CreationAction.OnDoneClick -> viewModelScope.launch {
                foodRepository.insert(
                    Food(
                        name = action.productText,
                        category = Category(
                            name = "a"
                        ),
                        quantity = 1
                    )
                )
            }
        }
    }
}