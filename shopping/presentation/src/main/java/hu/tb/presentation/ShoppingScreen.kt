package hu.tb.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.tb.domain.ShoppingItem
import hu.tb.presentation.components.Dialog
import hu.tb.presentation.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShoppingScreen(
    viewModel: ShoppingViewModel = koinViewModel()
) {
    ShoppingScreen(
        items = viewModel.items.collectAsStateWithLifecycle().value,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShoppingScreen(
    items: List<ShoppingItem>,
    onAction: (ShoppingAction) -> Unit
) {
    var isCreateDialogVisible by remember {
        mutableStateOf(false)
    }

    var isDeleteDialogVisible by remember {
        mutableStateOf(false)
    }

    Scaffold(
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
                    IconButton(
                        modifier = Modifier,
                        onClick = { isDeleteDialogVisible = true }
                    ) {
                        Icon(
                            Icons.Outlined.Delete,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            contentDescription = "clear icon"
                        )
                    }
                },
                colors = TopAppBarDefaults
                    .topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isCreateDialogVisible = true },
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentDescription = "add icon"
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = items
            ) { item ->
                ListItem(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .alpha(if (item.isChecked) 0.7f else 1f),
                    headlineContent = {
                        Text(
                            text = item.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingContent = {
                        Checkbox(
                            checked = item.isChecked,
                            onCheckedChange = { isChecked ->
                                onAction(
                                    ShoppingAction.OnItemCheckChange(
                                        item,
                                        isChecked
                                    )
                                )
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colorScheme.primary,
                                checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                                uncheckedColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        }

        if (isCreateDialogVisible) {
            CreateTodoDialog(
                onSaveButton = { onAction(ShoppingAction.OnCreateDialogSaveButtonClick(it)) },
                onDismissRequest = { isCreateDialogVisible = false }
            )
        }

        if (isDeleteDialogVisible) {
            ClearItemsDialog(
                onDeleteButton = { onAction(ShoppingAction.OnClearButtonClick) },
                onDismissRequest = { isDeleteDialogVisible = false }
            )
        }
    }
}

@Composable
private fun CreateTodoDialog(
    onSaveButton: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    var itemText by remember {
        mutableStateOf("")
    }

    Dialog(
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
    Dialog(
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

@Preview
@Composable
private fun CreateTodoDialogPreview() {
    AppTheme {
        Column {
            CreateTodoDialog(
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
    val mockList = listOf<ShoppingItem>(
        ShoppingItem(
            1, "test", false
        ),
        ShoppingItem(
            2, "test", true
        )
    )

    AppTheme {
        ShoppingScreen(
            items = mockList,
            onAction = {}
        )
    }
}