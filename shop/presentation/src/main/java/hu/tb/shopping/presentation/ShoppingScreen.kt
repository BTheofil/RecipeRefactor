package hu.tb.shopping.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.shop.ShopItem
import hu.tb.presentation.components.ProductCreation
import hu.tb.presentation.theme.AppTheme
import hu.tb.shopping.presentation.components.ShoppingDeleteDialog
import hu.tb.shopping.presentation.components.ShoppingEmptyScreen
import hu.tb.shopping.presentation.components.ShoppingItem
import hu.tb.shopping.presentation.components.ShoppingTopBar
import hu.tb.shopping.presentation.components.ShoppingTopBarAction
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShoppingScreen(
    viewModel: ShoppingViewModel = koinViewModel()
) {
    ShoppingScreen(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ShoppingScreen(
    state: ShoppingState,
    onAction: (ShoppingAction) -> Unit
) {
    var isDeleteDialogVisible by remember { mutableStateOf(false) }

    val sheetState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current

    BottomSheetScaffold(
        modifier = Modifier
            .clickable(
                onClick = {
                    focusManager.clearFocus()
                },
                interactionSource = null,
                indication = null
            ),
        topBar = {
            ShoppingTopBar(
                onAction = { action ->
                    when (action) {
                        ShoppingTopBarAction.AddShoppingItem -> scope.launch {
                            sheetState.bottomSheetState.expand()
                        }

                        ShoppingTopBarAction.ClearBoard -> {
                            isDeleteDialogVisible = true
                        }
                    }
                }
            )
        },
        sheetContent = {
            ProductCreation(
                onProductCreated = { onAction(ShoppingAction.SaveItem(it)) }
            )
        },
        scaffoldState = sheetState,
        sheetPeekHeight = 0.dp,
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (state.uncheckedItems.isEmpty()) {
                    item {
                        ShoppingEmptyScreen()
                    }
                } else {
                    items(
                        items = state.uncheckedItems,
                        key = { it -> it.id ?: it.hashCode() }
                    ) { item ->
                        ShoppingItem(
                            modifier = Modifier
                                .animateItem(),
                            item = item,
                            onCheckClick = { isChecked ->
                                onAction(ShoppingAction.ShopItemChange(item.copy(isChecked = isChecked)))
                            }
                        )
                    }
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
                        ShoppingItem(
                            modifier = Modifier
                                .animateItem(),
                            item = item,
                            onCheckClick = { isChecked ->
                                onAction(
                                    ShoppingAction.ShopItemChange(
                                        item.copy(isChecked = isChecked)
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    )

    if (isDeleteDialogVisible) {
        ShoppingDeleteDialog(
            onDeleteButton = { onAction(ShoppingAction.OnClearButtonClick) },
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
            quantity = 2,
            measure = Measure.PIECE,
            isChecked = false
        )
    }
    val mockList2 = List(2) { i ->
        ShopItem(
            id = (i + 2).toLong(),
            name = "$i name",
            quantity = 2,
            measure = Measure.PIECE,
            isChecked = false
        )
    }

    AppTheme {
        ShoppingScreen(
            state = ShoppingState(
                uncheckedItems = mockList1,
                checkedItems = mockList2
            ),
            onAction = {}
        )
    }
}