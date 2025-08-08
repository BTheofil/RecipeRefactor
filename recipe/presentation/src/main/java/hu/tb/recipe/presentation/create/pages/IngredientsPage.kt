package hu.tb.recipe.presentation.create.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.product.Product
import hu.tb.presentation.theme.AppTheme
import hu.tb.recipe.presentation.R
import hu.tb.recipe.presentation.create.CreateAction
import hu.tb.recipe.presentation.create.components.TextFieldWithDropdownMenu

@Composable
fun IngredientsPage(
    ingredients: List<Product?>,
    onAction: (CreateAction.IngredientsAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
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
                                    text = enumToString(product.measure),
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
        IngredientsCreatePanel(
            onAction = onAction
        )
    }
}

@Composable
fun IngredientsCreatePanel(
    onAction: (CreateAction.IngredientsAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    var name by remember {
        mutableStateOf("")
    }

    var quantity by remember {
        mutableStateOf("")
    }

    var measure by remember {
        mutableStateOf(Measure.PIECE)
    }

    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            label = {
                Text(
                    text = "Ingredient name",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f),
                value = quantity,
                onValueChange = { quantity = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(
                        text = "Amount",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                singleLine = true,
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextFieldWithDropdownMenu(
                modifier = Modifier
                    .weight(1f),
                textFieldValue = enumToString(measure),
                labelFieldText = stringResource(R.string.measurement),
                menuItemList = Measure.entries,
                onMenuItemClick = {
                    measure = it as Measure
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                focusManager.clearFocus()
                onAction(
                    CreateAction.IngredientsAction.OnAddIngredients(
                        Product(
                            name = name,
                            quantity = quantity.toInt(),
                            measure = measure
                        )
                    )
                )
                name = ""
                quantity = ""
            }) {
            Text(text = "Add ingredient")
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onAction(CreateAction.IngredientsAction.OnNextPage)
                name = ""
                quantity = ""
            }
        ) {
            Text(
                text = "Next page",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

private fun enumToString(measure: Measure): String =
    when (measure) {
        Measure.GRAM -> "gram"
        Measure.DAG -> "dag"
        Measure.KG -> "kg"
        Measure.PIECE -> "piece"
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