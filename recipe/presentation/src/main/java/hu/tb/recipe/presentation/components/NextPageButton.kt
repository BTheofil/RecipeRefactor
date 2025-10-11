package hu.tb.recipe.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import hu.tb.presentation.theme.Icon

@Composable
fun NextPageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = "Next page",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Icon(
            painter = painterResource(id = Icon.arrow_right),
            contentDescription = "arrow right",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}