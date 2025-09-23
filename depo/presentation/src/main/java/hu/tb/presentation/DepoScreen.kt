package hu.tb.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.product.Product
import hu.tb.presentation.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DepoScreen(
    viewModel: DepoViewModel = koinViewModel(),
    productCreateScreenRequest: () -> Unit
) {
    DepoScreen(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        action = {
            when (it) {
                DepoAction.OnAddFoodClick -> productCreateScreenRequest()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DepoScreen(
    state: DepoState,
    action: (DepoAction) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My products",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                },
                actions = {
                    IconButton(
                        onClick = { action(DepoAction.OnAddFoodClick) }
                    ) {
                        Icon(
                            Icons.Outlined.Add, contentDescription = "add product icon",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = state.products
                ) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = item.name
                            )
                            Text(item.quantity.toString())
                            Spacer(Modifier.width(4.dp))
                            Text(item.measure.toDisplay)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun StorageScreenPreview() {

    val mockState = DepoState(
        products = listOf(
            Product(name = "apple", quantity = 1.0, measure = Measure.PIECE),
            Product(name = "potato", quantity = 2.0, measure = Measure.KG),
        )
    )


    AppTheme {
        DepoScreen(
            state = mockState,
            action = {}
        )
    }
}