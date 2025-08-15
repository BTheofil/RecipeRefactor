package hu.tb.shopping.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.shop.ShopItem
import hu.tb.presentation.theme.AppTheme

@Composable
fun ShoppingItem(
    modifier: Modifier = Modifier,
    item: ShopItem,
    onCheckClick: (Boolean) -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .alpha(if (item.isChecked) 0.7f else 1f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isChecked,
                onCheckedChange = onCheckClick,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                    uncheckedColor = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                modifier = Modifier
                    .weight(1f),
                text = item.name,
                style = MaterialTheme.typography.bodyLarge
                    .copy(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None
                    ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = item.quantity.toString() + " " + item.measure,
                style = MaterialTheme.typography.bodyMedium
                    .copy(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None
                    ),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview
@Composable
private fun ShoppingItemPreview() {
    val mockItem = ShopItem(
        id = 1L,
        name = "testing",
        quantity = 1,
        measure = Measure.PIECE,
        isChecked = true
    )

    AppTheme {
        Column(
            modifier = Modifier
                .padding(32.dp)
        ) {
            ShoppingItem(
                item = mockItem,
                onCheckClick = {}
            )
            Spacer(Modifier.height(16.dp))
            ShoppingItem(
                item = mockItem.copy(isChecked = false),
                onCheckClick = {}
            )
        }
    }
}