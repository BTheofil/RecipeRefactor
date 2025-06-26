package hu.tb.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.meal.Category
import hu.tb.core.domain.meal.Food
import hu.tb.core.domain.meal.FoodRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CreationViewModel(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _event = Channel<CreationEvent>()
    val event = _event.receiveAsFlow()

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
                _event.send(CreationEvent.ProductInserted)
            }
        }
    }
}