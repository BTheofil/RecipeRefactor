package hu.tb.recipe.presentation.create.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.core.domain.product.Measure

@Composable
fun <T> TextFieldWithDropdownMenu(
    modifier: Modifier = Modifier,
    textFieldValue: T,
    labelFieldText: String,
    menuItemList: List<T>,
    onMenuItemClick: (T) -> Unit,
) {
    var isDropdownMenuVisible by rememberSaveable { mutableStateOf(false) }
    var itemWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    itemWidth = with(density) { it.width.toDp() }
                },
            value = textFieldValue as String,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    text = labelFieldText,
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
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "measurements menu icon"
                    )
                }
            },
        )
        DropdownMenu(
            modifier = Modifier
                .width(itemWidth),
            expanded = isDropdownMenuVisible,
            onDismissRequest = { isDropdownMenuVisible = false }) {
            menuItemList.forEach {
                DropdownMenuItem(
                    text = { Text(text = convertGeneric(it)) },
                    onClick = {
                        onMenuItemClick(it)
                        isDropdownMenuVisible = false
                    })
            }
        }
    }
}

private fun <T> convertGeneric(input: T): String {
    if (input is Measure) {
        return input.name
    }
    return ""
}

@Preview
@Composable
private fun TextFieldWithDropdownMenuPreview() {

    var selected by remember {
        mutableStateOf(Measure.PIECE)
    }

    TextFieldWithDropdownMenu(
        textFieldValue = selected,
        labelFieldText = "Measurement",
        menuItemList = Measure.entries,
        onMenuItemClick = { selected = it }
    )
}