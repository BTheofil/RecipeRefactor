package hu.tb.shopping.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import hu.tb.presentation.components.SimpleDialog
import hu.tb.presentation.theme.AppTheme

@Composable
fun ShopDeleteDialog(
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
        positiveButtonText = "Delete",
        negativeButtonText = "Cancel",
        onPositiveClick = onDeleteButton,
        onDismissRequest = onDismissRequest
    )
}

@Preview
@Composable
private fun ShoppingDeleteDialogPreview() {
    AppTheme {
        ShopDeleteDialog(
            onDeleteButton = {},
            onDismissRequest = {}
        )
    }
}