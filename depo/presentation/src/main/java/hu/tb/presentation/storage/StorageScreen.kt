package hu.tb.presentation.storage

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.product.Product
import hu.tb.presentation.components.PlusButton
import hu.tb.presentation.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun StorageScreen(
    viewModel: StorageViewModel = koinViewModel(),
    onCreationRequested: () -> Unit
) {
    StorageScreen(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        action = { action ->
            when (action) {
                StorageAction.OnAddFoodClick -> onCreationRequested()
                else -> viewModel::onAction
            }
        }
    )
}

private const val SHAKE_EFFECT_MIN = -2f
private const val SHAKE_EFFECT_MAX = 2f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StorageScreen(
    state: StorageState,
    action: (StorageAction) -> Unit
) {
    var isGroupDeleteActive by remember { mutableStateOf(false) }

    val groupItemShakeValue = remember { Animatable(SHAKE_EFFECT_MIN) }

    LaunchedEffect(isGroupDeleteActive) {
        if (isGroupDeleteActive) {
            groupItemShakeValue.animateTo(
                targetValue = SHAKE_EFFECT_MAX,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
            )
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(title = {})
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Products",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
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
                item {
                    PlusButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = { action(StorageAction.OnAddFoodClick) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun StorageScreenPreview() {

    val mockState = StorageState(
        products = listOf(
            Product("apple", 1.0, Measure.PIECE),
            Product("potato", 2.0, Measure.KG),
        )
    )


    AppTheme {
        StorageScreen(
            state = mockState,
            action = {}
        )
    }
}