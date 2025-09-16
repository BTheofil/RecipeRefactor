package hu.tb.recipe.presentation.components.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.product.Product

class ExampleProductParameterProvider() : PreviewParameterProvider<List<Product>> {

    override val values: Sequence<List<Product>>
        get() = sequenceOf(
            listOf(
                Product(
                    1, "first", 1.0, Measure.PIECE
                ),
                Product(
                    2, "second", 5.0, Measure.KG
                ),
                Product(
                    3, "third", 70.0, Measure.DAG
                ),
                Product(
                    4, "fourth", 0.6, Measure.LITER
                ),
            )
        )
}