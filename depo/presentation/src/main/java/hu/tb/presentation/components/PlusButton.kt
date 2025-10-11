package hu.tb.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.presentation.theme.AppTheme
import hu.tb.presentation.theme.Icon

@Composable
fun PlusButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .border(
                1.dp,
                Color.Black.copy(alpha = 0.3f),
                RoundedCornerShape(16.dp)
            ),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(Icon.add),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "Create new group icon"
        )
    }
}

@Preview
@Composable
private fun PlusButtonPreview() {
    AppTheme {
        PlusButton(
            onClick = {}
        )
    }
}