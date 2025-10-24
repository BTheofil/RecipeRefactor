package hu.tb.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.tb.presentation.theme.AppTheme

@Composable
fun OutlineButtonPrimary(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
private fun OutlineButtonPrimaryPreview() {
    AppTheme {
        OutlineButtonPrimary(
            text = "Testing",
            onClick = {}
        )
    }
}