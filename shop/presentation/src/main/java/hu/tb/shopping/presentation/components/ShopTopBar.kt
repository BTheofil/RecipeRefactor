package hu.tb.shopping.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import hu.tb.presentation.theme.Icon

sealed interface ShopTopBarAction {
    data object ClearBoard : ShopTopBarAction
    data object ShopFinished : ShopTopBarAction
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopTopBar(
    isShoppingFinished: Boolean,
    onAction: (ShopTopBarAction) -> Unit
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
                            painter = painterResource(Icon.more_vert),
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
                        text = { Text("Clear the board") },
                        onClick = {
                            onAction(ShopTopBarAction.ClearBoard)
                            isMenuOpen = false
                        }
                    )
                    if(isShoppingFinished) {
                        DropdownMenuItem(
                            text = { Text("Finish shopping") },
                            onClick = {
                                onAction(ShopTopBarAction.ShopFinished)
                                isMenuOpen = false
                            }
                        )
                    }
                }
            }
        }
    )
}