package hu.tb.shopping.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.shop.ShopItem
import hu.tb.presentation.theme.AppTheme
import hu.tb.shopping.presentation.components.NewShopItem
import hu.tb.shopping.presentation.components.ShopDeleteDialog
import hu.tb.shopping.presentation.components.ShoppingEmptyScreen
import hu.tb.shopping.presentation.components.ShopFinishDialog
import hu.tb.shopping.presentation.components.DisplayItemWidget
import hu.tb.shopping.presentation.components.ShopTopBar
import hu.tb.shopping.presentation.components.ShopTopBarAction
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShopScreen(
    viewModel: ShopViewModel = koinViewModel()
) {
    var isFinishDialogVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            if (event is ShopEvent.ShowShopFinishedDialog) {
                isFinishDialogVisible = true
            }
        }
    }

    ShopScreen(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onAction = viewModel::onAction
    )

    if (isFinishDialogVisible) {
        ShopFinishDialog(
            onAddItemsClick = {
                viewModel.onAction(ShopAction.AddAllItemsToStorage)
                isFinishDialogVisible = false
            },
            onDismissRequest = { isFinishDialogVisible = false }
        )
    }
}

@Composable
fun ShopScreen(
    state: ShopState,
    onAction: (ShopAction) -> Unit
) {
    var isDeleteDialogVisible by remember { mutableStateOf(false) }
    var isProductCreationVisible by remember { mutableStateOf(false) }
    var editedItem by remember { mutableStateOf<ShopItem?>(null) }

    val scope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .clickable(
                onClick = { focusManager.clearFocus() },
                interactionSource = null,
                indication = null
            ),
        topBar = {
            ShopTopBar(
                isShoppingFinished = state.checkedItems.isNotEmpty() && state.uncheckedItems.isEmpty(),
                onAction = { action ->
                    when (action) {
                        ShopTopBarAction.ClearBoard -> {
                            isDeleteDialogVisible = true
                        }

                        ShopTopBarAction.ShopFinished -> onAction(ShopAction.AddAllItemsToStorage)
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
            ) {
                Spacer(Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    if (state.checkedItems.isEmpty() && state.uncheckedItems.isEmpty()) {
                        item {
                            ShoppingEmptyScreen()
                        }
                    }
                    items(
                        items = state.uncheckedItems,
                        key = { it -> it.id ?: it.hashCode() }
                    ) { item ->
                        DisplayItemWidget(
                            modifier = Modifier
                                .animateItem(),
                            item = item,
                            onCheckClick = { isChecked ->
                                onAction(ShopAction.ShopItemChange(item.copy(isChecked = isChecked)))
                            },
                            onDeleteClick = { onAction(ShopAction.DeleteItem(item)) },
                            onEditClick = {
                                scope.launch {
                                    editedItem = item
                                    isProductCreationVisible = true
                                }
                            }
                        )
                    }
                    if (state.checkedItems.isNotEmpty()) {
                        item {
                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(horizontal = 32.dp),
                                thickness = 2.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        items(
                            items = state.checkedItems,
                            key = { it -> it.id ?: it.hashCode() }
                        ) { item ->
                            DisplayItemWidget(
                                modifier = Modifier.animateItem(),
                                item = item,
                                onCheckClick = { isChecked ->
                                    onAction(
                                        ShopAction.ShopItemChange(
                                            item.copy(isChecked = isChecked)
                                        )
                                    )
                                },
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                        .background(Color.Transparent)
                ) {
                    if (isProductCreationVisible) {
                        NewShopItem(
                            editItem = editedItem,
                            onDone = { onAction(ShopAction.ShopItemChange(it)) },
                            onClose = {
                                isProductCreationVisible = false
                                editedItem = null
                            }
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent)
                                .padding(horizontal = 22.dp)
                                .padding(bottom = 8.dp)
                        ) {
                            OutlinedButton(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                onClick = { isProductCreationVisible = true },
                                content = {
                                    Text(
                                        text = "Create new item",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    )

    if (isDeleteDialogVisible) {
        ShopDeleteDialog(
            onDeleteButton = {
                onAction(ShopAction.DeleteAllItems)
                isDeleteDialogVisible = false
            },
            onDismissRequest = { isDeleteDialogVisible = false }
        )
    }
}

@Preview
@Composable
private fun ShoppingScreenPreview() {
    val mockList1 = List(2) { i ->
        ShopItem(
            id = i.toLong(),
            name = "$i name",
            quantity = 2.0,
            measure = Measure.PIECE,
            isChecked = false
        )
    }
    val mockList2 = List(2) { i ->
        ShopItem(
            id = (i + 2).toLong(),
            name = "$i name",
            quantity = 2.0,
            measure = Measure.PIECE,
            isChecked = false
        )
    }

    AppTheme {
        ShopScreen(
            state = ShopState(
                uncheckedItems = mockList1,
                checkedItems = mockList2
            ),
            onAction = {}
        )
    }
}