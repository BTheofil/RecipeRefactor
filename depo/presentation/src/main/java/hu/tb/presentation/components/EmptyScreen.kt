package hu.tb.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.tb.presentation.theme.AppTheme

@Composable
fun EmptyScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        FadedText(
            text = "No products in storage"
        )
    }
}

@Preview
@Composable
private fun EmptyScreenPreview() {
    AppTheme {
        EmptyScreen()
    }
}