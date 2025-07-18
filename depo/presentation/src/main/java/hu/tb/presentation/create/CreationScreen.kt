package hu.tb.presentation.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.core.domain.product.Measure
import hu.tb.presentation.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreationScreen(
    viewModel: CreationViewModel = koinViewModel(),
    finishCreation: () -> Unit
) {
    CreationScreen(
        action = { action ->
            when (action) {
                is CreationAction.OnDoneClick -> {
                    viewModel.onAction(action)
                    finishCreation()
                }
            }
        }
    )
}

@Composable
private fun CreationScreen(
    action: (CreationAction) -> Unit
) {
    var productText by remember {
        mutableStateOf("")
    }
    var quantity by remember {
        mutableIntStateOf(0)
    }
    var measure by remember {
        mutableStateOf(Measure.PIECE)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = productText,
            onValueChange = { productText = it },
            label = {
                Text(
                    text = "product name",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        )
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f),
                value = "",
                onValueChange = {},
                label = {
                    Text(
                        text = "Quantity",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
            )
            Spacer(Modifier.width(16.dp))
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f),
                value = "",
                onValueChange = {}
            )
            DropdownMenu(
                modifier = Modifier
                    .weight(1f),
                expanded = true,
                content = {},
                onDismissRequest = {}
            )
        }
        Button(
            onClick = {
                action(
                    CreationAction.OnDoneClick(
                        productText = productText,
                        quantity = quantity,
                        measure = measure
                    )
                )
            },
            content = {
                Text(
                    text = "Done",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
}

@Preview
@Composable
private fun CreationScreenPreview() {
    AppTheme {
        CreationScreen(
            action = { }
        )
    }
}