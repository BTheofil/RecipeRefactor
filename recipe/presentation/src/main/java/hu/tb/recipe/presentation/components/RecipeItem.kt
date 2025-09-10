package hu.tb.recipe.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.product.Product
import hu.tb.core.domain.recipe.Recipe
import hu.tb.presentation.theme.AppTheme

@Composable
fun RecipeItem(
    recipe: Recipe
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(4.dp))
            Row {
                Text(
                    text = "Total ingredients: " + recipe.ingredients.count(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = "Steps: " + recipe.howToMakeSteps.count(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecipeItemPreview() {
    val mockRecipe = Recipe(
        id = 1,
        name = "First meal",
        ingredients = listOf(
            Product(name = "apple", quantity = 1.0, measure = Measure.PIECE)
        ),
        howToMakeSteps = listOf("cut", "bake")
    )

    AppTheme {
        RecipeItem(mockRecipe)
    }
}