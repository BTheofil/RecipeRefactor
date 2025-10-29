package hu.tb.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.presentation.theme.AppTheme
import hu.tb.presentation.theme.Icon

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes icon: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(icon),
            tint = MaterialTheme.colorScheme.primary.copy(
                alpha = 0.6f
            ),
            contentDescription = "screen match icon"
        )
        Spacer(Modifier.height(16.dp))
        FadedText(
            text = text
        )
    }
}

@Preview
@Composable
private fun EmptyScreenPreview() {
    AppTheme {
        EmptyScreen(
            text = "Empty screen",
            icon = Icon.shopping_cart
        )
    }
}