package hu.tb.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.tb.presentation.theme.AppTheme

@Composable
fun FadedText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary.copy(
            alpha = 0.6f
        )
    )
}

@Preview
@Composable
private fun FadedTextPreview() {
    AppTheme {
        FadedText(text = "This is a text")
    }
}