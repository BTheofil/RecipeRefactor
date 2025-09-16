package hu.tb.shopping.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.shop.ShopItem
import hu.tb.presentation.theme.AppTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun DisplayItemWidget(
    modifier: Modifier = Modifier,
    item: ShopItem,
    onCheckClick: (Boolean) -> Unit,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    var offset by remember { mutableStateOf(Animatable(0f)) }
    var actionComponentWidth by remember { mutableFloatStateOf(0f) }
    var cardHeight by remember { mutableStateOf(0.dp) }
    var itemTextLineCount by remember { mutableIntStateOf(0) }

    val scope = rememberCoroutineScope()

    val localDensity = LocalDensity.current

    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .then(if (item.isChecked) Modifier.alpha(0f) else Modifier.alpha(1f))
                .height(IntrinsicSize.Max)
                .onSizeChanged {
                    actionComponentWidth = it.width.toFloat()
                }
                .clip(
                    RoundedCornerShape(
                        topStart = 14.dp,
                        bottomStart = 14.dp,
                    )
                )
                .height(cardHeight),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.primaryContainer),
                onClick = {
                    scope.launch {
                        onEditClick()
                        offset.animateTo(0f)
                    }
                },
                content = {
                    Icon(Icons.Outlined.Edit, "edit icon")
                }
            )
            IconButton(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.error),
                onClick = onDeleteClick,
                content = {
                    Icon(Icons.Outlined.Delete, "delete icon")
                }
            )
        }
        ElevatedCard(
            modifier = Modifier
                .onSizeChanged {
                    cardHeight = with(localDensity) { it.height.toDp() }
                }
                .offset { IntOffset(offset.value.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            if (item.isChecked) return@detectHorizontalDragGestures
                            scope.launch {
                                val newOffset =
                                    (offset.value + dragAmount).coerceIn(0f, actionComponentWidth)
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                if (offset.value >= actionComponentWidth / 2) {
                                    offset.animateTo(actionComponentWidth)
                                } else {
                                    offset.animateTo(0f)
                                }
                            }
                        }
                    )
                }
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
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = {
                        itemTextLineCount = it.lineCount
                    }
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    modifier = Modifier
                        .then(if (itemTextLineCount > 1) Modifier.align(Alignment.Top) else Modifier),
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
}

@Preview
@Composable
private fun ShoppingItemPreview() {
    val mockItem = ShopItem(
        id = 1L,
        name = "testing",
        quantity = 1.0,
        measure = Measure.PIECE,
        isChecked = true
    )

    AppTheme {
        Column(
            modifier = Modifier
                .padding(32.dp)
        ) {
            DisplayItemWidget(
                item = mockItem,
                onCheckClick = {},
                onEditClick = {},
                onDeleteClick = {}
            )
            Spacer(Modifier.height(16.dp))
            DisplayItemWidget(
                item = mockItem.copy(isChecked = false),
                onCheckClick = {},
                onEditClick = {},
                onDeleteClick = {}
            )
        }
    }
}