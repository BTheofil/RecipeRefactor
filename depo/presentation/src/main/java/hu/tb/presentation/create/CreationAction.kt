package hu.tb.presentation.create

import hu.tb.core.domain.product.Measure

sealed interface CreationAction {
    data class OnDoneClick(
        val productText: String,
        val quantity: Int,
        val measure: Measure
    ): CreationAction
}