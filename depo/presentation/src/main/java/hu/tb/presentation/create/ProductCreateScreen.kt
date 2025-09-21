package hu.tb.presentation.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.product.Product
import hu.tb.presentation.components.TextFieldWithDropdownMenu
import hu.tb.presentation.components.clearFocus
import hu.tb.presentation.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductCreateScreen(
    viewModel: ProductCreateViewModel = koinViewModel(),
    mainScreenRequest: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            if(event is Long) {
                mainScreenRequest()
            }
        }
    }

    ProductCreateScreen(
        onFinishClick = { viewModel.addNewProduct(it) }
    )
}

@Composable
fun ProductCreateScreen(
    onFinishClick: (Product) -> Unit
) {

    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("0") }
    var measure by remember { mutableStateOf(Measure.entries.first()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clearFocus()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            label = {
                Text(
                    text = "Product name",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = quantity,
            onValueChange = { quantity = it },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            label = {
                Text(
                    text = "Quantity",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        )
        TextFieldWithDropdownMenu(
            labelText = "Measurement",
            selectedItem = measure.toDisplay,
            menuItemList = Measure.entries,
            itemToDisplay = { it.toDisplay },
            onMenuItemClick = { measure = it }
        )
        Spacer(Modifier.height(64.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onFinishClick(
                    Product(
                        name = name,
                        quantity = quantity.toDouble(),
                        measure = measure
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Finish",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
private fun ProductCreateScreenRequestPreview() {
    AppTheme {
        ProductCreateScreen(
            onFinishClick = {}
        )
    }
}