package hu.tb.recipe.presentation.create.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.core.domain.product.Product
import hu.tb.presentation.components.ProductCreation
import hu.tb.presentation.theme.AppTheme
import hu.tb.recipe.presentation.components.NextPageButton
import hu.tb.recipe.presentation.create.CreateAction

@Composable
fun IngredientsPage(
    ingredients: List<Product?>,
    onAction: (CreateAction.IngredientsAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (ingredients.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Add some ingredients",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                itemsIndexed(
                    items = ingredients,
                ) { index, product ->
                    product?.let {
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = product.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            },
                            supportingContent = {
                                Row {
                                    Text(
                                        text = product.quantity.toString(),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = product.measure.toDisplay,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }
                            },
                            trailingContent = {
                                IconButton(onClick = {
                                    onAction(
                                        CreateAction.IngredientsAction.OnRemoveIngredient(
                                            index
                                        )
                                    )
                                }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = "remove selected ingredient icon",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            })
                    }
                }
            }
        }
        IngredientsCreatePanel(
            onAction = onAction
        )
    }
}

@Composable
fun IngredientsCreatePanel(
    onAction: (CreateAction.IngredientsAction) -> Unit
) {
    Column {
        ProductCreation(
            onProductCreated = { onAction(CreateAction.IngredientsAction.OnAddIngredients(it.toProduct())) }
        )
        NextPageButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { onAction(CreateAction.IngredientsAction.OnNextPage) }
        )
    }
}

@Preview
@Composable
private fun IngredientsPagePreview() {
    AppTheme {
        IngredientsPage(
            ingredients = emptyList(),
            onAction = {}
        )
    }
}