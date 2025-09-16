package hu.tb.recipe.presentation.create.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import hu.tb.core.domain.product.Product
import hu.tb.core.domain.product.ProductCreated
import hu.tb.presentation.components.ProductCreationComponent
import hu.tb.presentation.theme.AppTheme
import hu.tb.recipe.presentation.components.NextPageButton
import hu.tb.recipe.presentation.components.SuggestionSection
import hu.tb.recipe.presentation.components.preview.ExampleProductParameterProvider
import hu.tb.recipe.presentation.create.CreateAction

@Composable
fun IngredientsPage(
    ingredients: List<Product?>,
    suggestions: List<Product>,
    onAction: (CreateAction.IngredientsAction) -> Unit
) {
    var suggestedProductName by remember { mutableStateOf<String?>(null) }
    var pageHeight by remember { mutableStateOf(0.dp) }
    var ingredientCreatePanelHeight by remember { mutableStateOf(0.dp) }

    val focusManager = LocalFocusManager.current
    val density = LocalDensity.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = { focusManager.clearFocus() }
            )
            .onSizeChanged {
                pageHeight = with(density) { it.height.toDp() }
            }
    ) {
        if (suggestions.isNotEmpty()) {
            SuggestionSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .heightIn(max = (pageHeight - ingredientCreatePanelHeight) / 3),
                suggestions = suggestions,
                onChipClick = { suggestedProductName = it }
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
        ) {
            if (ingredients.isEmpty()) {
                item {
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
                }
            } else {
                itemsIndexed(
                    items = ingredients,
                    key = { index, item -> item?.id ?: index }
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
        IngredientCreatePanel(
            modifier = Modifier
                .onSizeChanged {
                    ingredientCreatePanelHeight = with(density) { it.height.toDp() }
                },
            prefillName = suggestedProductName,
            onAction = {
                onAction(it)
                focusManager.clearFocus()
            }
        )
    }
}

@Composable
fun IngredientCreatePanel(
    modifier: Modifier = Modifier,
    prefillName: String? = null,
    onAction: (CreateAction.IngredientsAction) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        ProductCreationComponent(
            prefillItem = prefillName?.let { ProductCreated(name = prefillName) },
            onProductCreated = { onAction(CreateAction.IngredientsAction.OnAddIngredients(it.toProduct())) }
        )
        NextPageButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { onAction(CreateAction.IngredientsAction.OnNextPage) }
        )
        Spacer(Modifier.height(8.dp))
    }
}

@Preview
@Composable
private fun IngredientsPagePreview(
    @PreviewParameter(ExampleProductParameterProvider::class) mockProducts: List<Product>
) {
    AppTheme {
        IngredientsPage(
            ingredients = emptyList(),
            suggestions = mockProducts,
            onAction = {}
        )
    }
}