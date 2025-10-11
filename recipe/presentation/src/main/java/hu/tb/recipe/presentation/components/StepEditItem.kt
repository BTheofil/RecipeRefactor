package hu.tb.recipe.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun StepEditItem(
    modifier: Modifier = Modifier,
    index: Int,
    text: String,
    onTextChange: (String) -> Unit,
    isRemoveIconVisible: Boolean,
    onRemoveClick: (index: Int) -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = (index + 1).toString(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(Modifier.width(16.dp))
            BasicTextField(
                modifier = Modifier
                    .weight(1f),
                value = text,
                onValueChange = onTextChange,
                decorationBox = { innerTextField ->
                    if (text.isEmpty()) {
                        Text("write your recipe steps...")
                    }
                    innerTextField()
                }
            )
            IconButton(
                modifier = Modifier
                    .graphicsLayer {
                        alpha = if (isRemoveIconVisible) {
                            1f
                        } else {
                            0f
                        }
                    },
                enabled = isRemoveIconVisible,
                onClick = {
                    onRemoveClick(index)
                }
            ) {
                Icon(
                    painterResource(hu.tb.presentation.R.drawable.close),
                    "close icon",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}