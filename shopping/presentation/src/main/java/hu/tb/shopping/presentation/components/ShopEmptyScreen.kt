package hu.tb.shopping.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.presentation.components.FadedText
import hu.tb.presentation.theme.AppTheme
import hu.tb.presentation.theme.Icon

@Composable
fun ShoppingEmptyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(Icon.shopping_cart),
            tint = MaterialTheme.colorScheme.primary.copy(
                alpha = 0.6f
            ),
            contentDescription = "shop cart empty icon"
        )
        Spacer(Modifier.height(16.dp))
        FadedText(
            text = "Your list is empty"
        )
    }
}

@Preview
@Composable
private fun ShoppingEmptyScreenPreview() {
    AppTheme {
        ShoppingEmptyScreen()
    }
}