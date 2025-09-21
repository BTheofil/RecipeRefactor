package hu.tb.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun Modifier.clearFocus(): Modifier {
    val focusManager = LocalFocusManager.current
    return this then Modifier
        .clickable(
            onClick = { focusManager.clearFocus() },
            interactionSource = null,
            indication = null
        )
}