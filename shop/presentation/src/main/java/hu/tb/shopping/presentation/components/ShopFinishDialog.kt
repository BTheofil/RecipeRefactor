package hu.tb.shopping.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import hu.tb.presentation.components.SimpleDialog
import hu.tb.presentation.theme.AppTheme

@Composable
fun ShopFinishDialog(
    onAddItemsClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    SimpleDialog(
        icon = Icons.Outlined.CheckCircle,
        title = "Adding all bought items",
        content = {
            Text(
                text = "This action will all bought items to the storage.\n" +
                        "Are you want to proceed?",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        positiveButtonText = "Add",
        negativeButtonText = "Cancel",
        onPositiveClick = onAddItemsClick,
        onDismissRequest = onDismissRequest
    )
}

@Preview
@Composable
private fun ShoppingFinishDialogPreview() {
    AppTheme {
        ShopFinishDialog(
            onAddItemsClick = {},
            onDismissRequest = {}
        )
    }
}