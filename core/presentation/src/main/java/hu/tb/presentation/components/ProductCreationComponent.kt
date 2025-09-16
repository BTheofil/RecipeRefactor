package hu.tb.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.product.ProductCreated
import hu.tb.presentation.theme.AppTheme

@Composable
fun ProductCreationComponent(
    modifier: Modifier = Modifier,
    prefillItem: ProductCreated? = null,
    onProductCreated: (ProductCreated) -> Unit
) {
    var name by remember(prefillItem) { mutableStateOf(prefillItem?.name ?: "") }
    var quantity by remember(prefillItem) {
        mutableStateOf(
            prefillItem?.quantity?.toString() ?: ""
        )
    }
    var measure by remember(prefillItem) { mutableStateOf(prefillItem?.measure ?: Measure.PIECE) }
    var isNameError by remember { mutableStateOf(false) }
    var isQuantityError by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier,
    ) {
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
            isError = isNameError
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
                label = {
                    Text(
                        text = "Amount",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                isError = isQuantityError
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextFieldWithDropdownMenu(
                modifier = Modifier
                    .weight(1f),
                labelText = "Measurement",
                selectedItem = measure.toDisplay,
                menuItemList = Measure.entries,
                onMenuItemClick = { measure = it },
                itemToDisplay = { it.name }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                focusManager.clearFocus()
                try {
                    require(name.isNotEmpty())
                    isNameError = false
                    require(quantity.isNotEmpty())
                    isQuantityError = false
                    onProductCreated(
                        ProductCreated(
                            id = prefillItem?.id,
                            name = name,
                            quantity = quantity.toDouble(),
                            measure = measure
                        )
                    )
                    name = ""
                    quantity = ""
                } catch (_: Exception) {
                    if (name.isEmpty()) isNameError = true
                    isQuantityError = quantity.isEmpty()
                }
            }) {
            Text(text = "Add ingredient")
        }
    }
}

@Preview
@Composable
private fun ProductCreationPreview() {
    AppTheme {
        ProductCreationComponent(
            onProductCreated = {}
        )
    }
}