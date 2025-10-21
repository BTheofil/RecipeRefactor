package hu.tb.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.presentation.theme.AppTheme
import hu.tb.presentation.theme.Icon

@Composable
fun <T> TextFieldWithDropdownMenu(
    modifier: Modifier = Modifier,
    selectedItem: String,
    labelText: String,
    menuItemList: List<T>,
    onMenuItemClick: (T) -> Unit,
    itemToDisplay: (T) -> String
) {
    var isDropdownMenuVisible by rememberSaveable { mutableStateOf(false) }
    var itemWidth by remember { mutableStateOf(0.dp) }

    val density = LocalDensity.current
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    itemWidth = with(density) { it.width.toDp() }
                }
                .onFocusChanged {
                    if (it.isFocused) {
                        focusManager.clearFocus()
                        isDropdownMenuVisible = true
                    }
                },
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    text = labelText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                IconButton(
                    modifier = Modifier
                        .testTag("DropdownMenuIconTag"),
                    onClick = { isDropdownMenuVisible = true }) {
                    Icon(
                        modifier = Modifier,
                        painter = painterResource(Icon.more_vert),
                        contentDescription = "measurements menu icon"
                    )
                }
            },
        )
        DropdownMenu(
            modifier = Modifier
                .width(itemWidth),
            expanded = isDropdownMenuVisible,
            onDismissRequest = { isDropdownMenuVisible = false },
            content = {
                menuItemList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(itemToDisplay(item)) },
                        onClick = {
                            onMenuItemClick(item)
                            isDropdownMenuVisible = false
                        }
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun TextFieldWithDropdownMenuPreview() {

    val mockList = listOf("one", "two", "three")

    var selected by remember {
        mutableStateOf(mockList.first())
    }

    AppTheme {
        TextFieldWithDropdownMenu(
            selectedItem = selected,
            labelText = "Measurement",
            menuItemList = mockList,
            onMenuItemClick = { selected = it },
            itemToDisplay = { it }
        )
    }
}