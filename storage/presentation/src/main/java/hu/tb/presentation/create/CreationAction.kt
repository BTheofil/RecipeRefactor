package hu.tb.presentation.create

import hu.tb.core.domain.meal.FoodCategory
import hu.tb.core.domain.meal.Measure

sealed interface CreationAction {
    data class OnDoneClick(
        val productText: String,
        val selectedCategory: FoodCategory,
        val quantity: Int,
        val measure: Measure
    ): CreationAction
}