package hu.tb.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import hu.tb.presentation.theme.AppTheme

@Composable
fun EmptyScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No products in storage",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.3f),
            textAlign = TextAlign.Center
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