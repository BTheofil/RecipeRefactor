package hu.tb.shopping.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.shop.ShopItem
import hu.tb.presentation.components.ProductCreation
import hu.tb.presentation.components.SimpleDialog
import hu.tb.presentation.theme.AppTheme
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
    var isSingleDeleteDialogVisible by remember { mutableStateOf(false) }
    var isMenuOpen by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<ShopItem?>(null) }

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
            TopAppBar(
                title = {
                    Text(
                        text = "Shopping list",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        IconButton(
                            onClick = { isMenuOpen = true },
                            content = {
                                Icon(
                                    imageVector = Icons.Outlined.MoreVert,
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = "menu icon",
                                )
                            }
                        )
                        DropdownMenu(
                            expanded = isMenuOpen,
                            onDismissRequest = { isMenuOpen = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Add item") },
                                onClick = {
                                    scope.launch {
                                        sheetState.bottomSheetState.expand()
                                    }
                                    isMenuOpen = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Clear the board") },
                                onClick = { isDeleteDialogVisible = true }
                            )
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
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp),
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
                        ItemContainer(
                            modifier = Modifier
                                .animateItem(),
                            item = item,
                            onTextChange = { newText ->
                                onAction(ShoppingAction.ShopItemChange(item.copy(name = newText)))
                            },
                            onCheckClick = { isChecked ->
                                onAction(ShoppingAction.ShopItemChange(item.copy(isChecked = isChecked)))
                            }
                        )
                    }
                }
                if (state.checkedItems.isNotEmpty()) {
                    item {
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    items(
                        items = state.checkedItems,
                        key = { it -> it.id ?: it.hashCode() }
                    ) { item ->
                        ItemContainer(
                            modifier = Modifier
                                .animateItem()
                                .combinedClickable(
                                    onClick = {},
                                    onLongClick = {
                                        isSingleDeleteDialogVisible = true
                                        selectedItem = item
                                    },
                                    onLongClickLabel = "appear single delete dialog",
                                    interactionSource = null,
                                    indication = null
                                ),
                            item = item,
                            onTextChange = {},
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
        ClearItemsDialog(
            onDeleteButton = { onAction(ShoppingAction.OnClearButtonClick) },
            onDismissRequest = { isDeleteDialogVisible = false }
        )
    }

    if (isSingleDeleteDialogVisible) {
        DeleteSingleItemDialog(
            onDeleteButton = {
                selectedItem?.let { onAction(ShoppingAction.OnDeleteSingleButtonClick(it)) }
                selectedItem = null
                isSingleDeleteDialogVisible = false
            },
            onDismissRequest = {
                isSingleDeleteDialogVisible = false
                selectedItem = null
            }
        )
    }
}

@Composable
private fun ShoppingEmptyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.ShoppingCart,
            tint = MaterialTheme.colorScheme.primary.copy(
                alpha = 0.6f
            ),
            contentDescription = "shop cart empty icon"
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Your list is empty.",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary.copy(
                alpha = 0.6f
            )
        )
    }
}

@Composable
private fun ItemContainer(
    modifier: Modifier,
    item: ShopItem,
    onTextChange: (String) -> Unit,
    onCheckClick: (Boolean) -> Unit
) {
    var currentText by remember((item.name)) {
        mutableStateOf(item.name)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (item.isChecked) 0.7f else 1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )
        Spacer(Modifier.width(8.dp))
        BasicTextField(
            modifier = Modifier
                .weight(1f),
            value = currentText,
            onValueChange = {
                currentText = it
                onTextChange(currentText)
            },
            textStyle = MaterialTheme.typography.bodyLarge
                .copy(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None
                ),
            maxLines = 1,
            enabled = !item.isChecked
        )
        Checkbox(
            checked = item.isChecked,
            onCheckedChange = onCheckClick,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                uncheckedColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
private fun CreateShoppingItemDialog(
    onSaveButton: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    var itemText by remember {
        mutableStateOf("")
    }

    SimpleDialog(
        icon = Icons.Outlined.Create,
        title = "Create new item",
        content = {
            OutlinedTextField(
                value = itemText,
                onValueChange = { itemText = it },
                maxLines = 1,
                placeholder = {
                    Text(
                        text = "New item name"
                    )
                },
            )
        },
        positiveTitleButton = "Add",
        negativeTitleButton = "Discard",
        onPositiveButton = {
            onSaveButton(itemText)
            onDismissRequest()
        },
        onDismissRequest = onDismissRequest
    )
}

@Composable
private fun ClearItemsDialog(
    onDeleteButton: () -> Unit,
    onDismissRequest: () -> Unit
) {
    SimpleDialog(
        icon = Icons.Outlined.Delete,
        title = "Clear items",
        content = {
            Text(
                text = "This action will permanently delete all written items.\n" +
                        "Are you sure you want to proceed?",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        positiveTitleButton = "Delete",
        negativeTitleButton = "Cancel",
        onPositiveButton = {
            onDeleteButton()
            onDismissRequest()
        },
        onDismissRequest = onDismissRequest
    )
}

@Composable
private fun DeleteSingleItemDialog(
    onDeleteButton: () -> Unit,
    onDismissRequest: () -> Unit
) {
    SimpleDialog(
        icon = Icons.Outlined.Delete,
        title = "Delete item",
        content = {
            Text(
                text = "This action will permanently delete the item.\n" +
                        "Are you sure you want to proceed?",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        positiveTitleButton = "Delete",
        negativeTitleButton = "Cancel",
        onPositiveButton = {
            onDeleteButton()
            onDismissRequest()
        },
        onDismissRequest = onDismissRequest
    )
}

@Preview
@Composable
private fun CreateTodoDialogPreview() {
    AppTheme {
        Column {
            CreateShoppingItemDialog(
                onDismissRequest = {},
                onSaveButton = {}
            )
        }
    }
}

@Preview
@Composable
private fun ClearItemsDialogPreview() {
    AppTheme {
        ClearItemsDialog(
            onDeleteButton = {},
            onDismissRequest = {}
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
            id = i.toLong(),
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