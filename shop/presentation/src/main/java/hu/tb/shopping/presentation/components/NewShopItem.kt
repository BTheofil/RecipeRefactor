package hu.tb.shopping.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.core.domain.shop.ShopItem
import hu.tb.presentation.components.ProductCreationComponent
import hu.tb.presentation.theme.AppTheme

@Composable
fun NewShopItem(
    editItem: ShopItem? = null,
    onDone: (ShopItem) -> Unit,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp))
            .background(MaterialTheme.colorScheme.background)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)
            )
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(
                    modifier = Modifier
                        .testTag("closeIcon"),
                    onClick = onClose
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "close icon",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            ProductCreationComponent(
                prefillItem = editItem?.toProductCreation(),
                onProductCreated = { onDone(it.toShopItem()) }
            )
            Spacer(Modifier.height(4.dp))
        }
    }
}

@Preview
@Composable
private fun NewShopItemPreview() {
    AppTheme {
        NewShopItem(
            onDone = {},
            onClose = {}
        )
    }
}