package hu.tb.recipe.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.product.Product
import hu.tb.core.domain.recipe.Recipe
import hu.tb.core.domain.step.Step
import hu.tb.presentation.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = koinViewModel()
) {
    DetailScreen(
        recipe = viewModel.recipe.value,
        makeRecipeClick = { viewModel.makeRecipeToProduct() }
    )
}

@Composable
private fun DetailScreen(
    recipe: Recipe?,
    makeRecipeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .scrollable(rememberScrollState(), Orientation.Horizontal)
    ) {
        if (recipe == null) {
            CircularProgressIndicator()
        } else {
            SectionBackground(
                content = {
                    Text(
                        text = recipe.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
            Spacer(Modifier.height(16.dp))
            SectionBackground(
                content = {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(
                            items = recipe.ingredients,
                            key = { it.id!! }
                        ) { ingredient ->
                            IngredientItem(ingredient)
                        }
                    }
                }
            )
            Spacer(Modifier.height(16.dp))
            SectionBackground(
                content = {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(
                            items = recipe.howToMakeSteps,
                            key = { index, item -> item.id!! }
                        ) { index, step ->
                            NumberedStep(
                                index = index + 1,
                                description = step.description
                            )
                        }
                    }
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = makeRecipeClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    content = {
                        Text(
                            text = "Make recipe",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun IngredientItem(
    ingredient: Product
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "- " + ingredient.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = ingredient.quantity.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = ingredient.measure.toDisplay,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
private fun NumberedStep(
    index: Int,
    description: String,
) {
    var stepLineCount by remember { mutableIntStateOf(-1) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = if (stepLineCount == 1) Alignment.CenterVertically else Alignment.Top,
    ) {
        Text(
            text = "$index.",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(8.dp))
        Text(
            modifier = Modifier
                .height(IntrinsicSize.Max),
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            onTextLayout = {
                stepLineCount = it.lineCount
            }
        )
    }
}

@Composable
private fun SectionBackground(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview
@Composable
private fun DetailScreenPreview() {
    val mockRecipe = Recipe(
        id = 1,
        name = "First meal",
        ingredients = listOf(
            Product(id = 1, name = "apple", quantity = 1.0, measure = Measure.PIECE)
        ),
        howToMakeSteps = listOf(
            Step(id = 1, description = "cut"),
            Step(id = 2, description = "bake")
        )
    )

    AppTheme {
        DetailScreen(mockRecipe, makeRecipeClick = {})
    }
}