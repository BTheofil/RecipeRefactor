package hu.tb.presentation.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
            if (event is Long) {
                mainScreenRequest()
            }
        }
    }

    ProductCreateScreen(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        action = {
            when (it) {
                is ProductCreateAction.AddNewProduct -> viewModel.addNewProduct(it.product)
                ProductCreateAction.CloseScreen -> mainScreenRequest()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCreateScreen(
    state: ProductCreateState,
    action: (ProductCreateAction) -> Unit
) {

    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf(0.toString()) }
    var measure by remember { mutableStateOf(Measure.entries.first()) }

    val focusManager = LocalFocusManager.current

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(
                        onClick = { action(ProductCreateAction.CloseScreen) }
                    ) {
                        Icon(
                            Icons.Outlined.Close, contentDescription = "close icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clearFocus()
                .padding(innerPadding)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                isError = state.isNameError,
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
                isError = state.isQuantityError,
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
                onMenuItemClick = {
                    measure = it
                    focusManager.clearFocus()
                }
            )
            Spacer(Modifier.height(64.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    action(
                        ProductCreateAction.AddNewProduct(
                            Product(
                                name = name,
                                quantity = quantity.toDouble(),
                                measure = measure
                            )
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
}

@Preview
@Composable
private fun ProductCreateScreenRequestPreview() {
    AppTheme {
        ProductCreateScreen(
            state = ProductCreateState(),
            action = {}
        )
    }
}