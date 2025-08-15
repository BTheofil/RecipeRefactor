package hu.tb.shopping.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

sealed interface ShoppingTopBarAction {
    data object AddShoppingItem : ShoppingTopBarAction
    data object ClearBoard : ShoppingTopBarAction
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingTopBar(
    onAction: (ShoppingTopBarAction) -> Unit
) {
    var isMenuOpen by remember { mutableStateOf(false) }

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
                            onAction(ShoppingTopBarAction.AddShoppingItem)
                            isMenuOpen = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Clear the board") },
                        onClick = {
                            onAction(ShoppingTopBarAction.ClearBoard)
                            isMenuOpen = false
                        }
                    )
                }
            }
        }
    )
}