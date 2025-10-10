package hu.tb.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import hu.tb.presentation.R
import hu.tb.presentation.theme.AppTheme

@Composable
fun SimpleDialog(
    @DrawableRes icon: Int? = null,
    title: String,
    content: @Composable () -> Unit,
    positiveButtonText: String,
    onPositiveClick: () -> Unit,
    negativeButtonText: String,
    onDismissRequest: () -> Unit
) = Dialog(
    onDismissRequest = onDismissRequest
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(28.dp)),
        color = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            icon?.let {
                Icon(
                    painter = painterResource(it),
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = "dialog icon"
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(16.dp))
            content()
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(
                    onClick = onDismissRequest,
                    content = {
                        Text(
                            text = negativeButtonText,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                )
                Spacer(Modifier.width(16.dp))
                TextButton(
                    onClick = {
                        onPositiveClick()
                        onDismissRequest()
                    },
                    content = {
                        Text(
                            text = positiveButtonText,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun DialogPreview() {
    AppTheme {
        SimpleDialog(
            icon = R.drawable.check_circle,
            title = "New item",
            content = {
                Text("Dialog content")
            },
            positiveButtonText = "Add",
            onPositiveClick = {},
            negativeButtonText = "Cancel",
            onDismissRequest = {}
        )
    }
}