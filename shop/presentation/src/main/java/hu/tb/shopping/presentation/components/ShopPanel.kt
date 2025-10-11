package hu.tb.shopping.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.core.domain.shop.ShopItem
import hu.tb.presentation.components.ProductCreationComponent
import hu.tb.presentation.theme.AppTheme
import hu.tb.presentation.theme.Icon

@Composable
fun ShopPanel(
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
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = if (editItem != null) Arrangement.SpaceBetween else Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                editItem?.let {
                    Text(
                        text = "Modify item",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(
                    modifier = Modifier
                        .testTag("closeIcon"),
                    onClick = onClose
                ) {
                    Icon(
                        painter = painterResource(Icon.close),
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
private fun ShopPanelPreview() {
    AppTheme {
        ShopPanel(
            onDone = {},
            onClose = {}
        )
    }
}