package hu.tb.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import hu.tb.presentation.R
import hu.tb.presentation.theme.AppTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun DisplayItemWithMenu(
    modifier: Modifier = Modifier,
    menuOptions: @Composable () -> Unit,
    displayContent: @Composable () -> Unit
) {
    val offset = remember { Animatable(0f) }
    var actionComponentWidth by remember { mutableFloatStateOf(0f) }
    var cardHeight by remember { mutableStateOf(0.dp) }

    val scope = rememberCoroutineScope()

    val density = LocalDensity.current

    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .onSizeChanged {
                    actionComponentWidth = with(density) { it.width.toFloat() }
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
            menuOptions()
        }
        ElevatedCard(
            modifier = Modifier
                .onSizeChanged {
                    cardHeight = with(density) { it.height.toDp() }
                }
                .offset { IntOffset(offset.value.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
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
        ) {
            displayContent()
        }
    }
}

@Preview
@Composable
private fun DisplayItemWithMenuPreview() {
    AppTheme {
        DisplayItemWithMenu(
            menuOptions = {
                IconButton(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    onClick = {},
                    content = {
                        Icon(painterResource(R.drawable.check_circle), "edit icon")
                    }
                )
            },
            displayContent = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("item")
                }
            }
        )
    }
}