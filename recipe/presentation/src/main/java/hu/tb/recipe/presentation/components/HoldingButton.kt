package hu.tb.recipe.presentation.components

import android.util.Log
import android.view.MotionEvent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import hu.tb.presentation.theme.AppTheme
import kotlinx.coroutines.launch

private const val MAX_TIME_TO_COMPLETE = 4000
private const val TIME_RELEASE_PROGRESS = 2000

@Composable
fun HoldingButton(
    modifier: Modifier = Modifier,
    onComplete: () -> Unit = {},
    content: @Composable RowScope.() -> Unit,
    coverColor: Color = MaterialTheme.colorScheme.secondary,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    borderStroke: BorderStroke = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
) {
    val fractionAnim = remember { Animatable(0f) }
    var componentSize by remember { mutableStateOf(IntSize.Zero) }

    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    Surface(
        modifier = modifier
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        scope.launch {
                            fractionAnim.animateTo(
                                1f,
                                animationSpec = tween(
                                    durationMillis = (MAX_TIME_TO_COMPLETE * (1 - fractionAnim.value)).toInt()
                                )
                            )
                        }
                    }

                    MotionEvent.ACTION_UP -> {
                        val wasCompleted = fractionAnim.value >= 0.97f
                        scope.launch {
                            fractionAnim.animateTo(
                                0f,
                                animationSpec = tween(
                                    durationMillis = TIME_RELEASE_PROGRESS,
                                    easing = LinearEasing
                                )
                            )
                        }
                        if (wasCompleted) {
                            onComplete()
                        }
                    }

                    MotionEvent.ACTION_CANCEL -> {
                        scope.launch {
                            fractionAnim.animateTo(
                                0f,
                                animationSpec = tween(
                                    durationMillis = TIME_RELEASE_PROGRESS,
                                    easing = LinearEasing
                                )
                            )
                        }
                    }
                }
                true
            }
            .onSizeChanged {
                componentSize = it
            },
        shape = ButtonDefaults.shape,
        contentColor = containerColor,
        border = borderStroke
    ) {
        Canvas(
            modifier = Modifier
                .width(with(density) { componentSize.width.toDp() })
                .height(with(density) { componentSize.height.toDp() }),
            onDraw = {
                drawRect(
                    color = coverColor,
                    size = Size(
                        width = fractionAnim.value * size.width,
                        height = size.height
                    )
                )
            },
        )
        Row(
            Modifier
                .defaultMinSize(
                    minWidth = ButtonDefaults.MinWidth,
                    minHeight = ButtonDefaults.MinHeight,
                )
                .padding(ButtonDefaults.ContentPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Preview
@Composable
private fun HoldingButtonPreview() {
    AppTheme {
        Column {
            HoldingButton(
                onComplete = {
                    Log.d("MYTAG", "click event")
                },
                content = {
                    Text(
                        text = "testing"
                    )
                },
            )
            Button(
                onClick = {}
            ) {
                Text(
                    text = "testing"
                )
            }
        }
    }
}