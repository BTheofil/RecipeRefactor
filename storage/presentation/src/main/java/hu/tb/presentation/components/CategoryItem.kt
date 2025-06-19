package hu.tb.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    deleteIconPadding: Dp = 8.dp,
    title: String,
    onGroupItemClick: () -> Unit,
    onEditGroupClick: () -> Unit,
    onDeleteGroupClick: () -> Unit,
    isDeleteActive: Boolean = false,
    isGroupSelected: Boolean = false,
) {
    Box(
        modifier = modifier
            .padding(top = deleteIconPadding)
            .then(
                if (isGroupSelected) {
                    Modifier
                        .border(4.dp, MaterialTheme.colorScheme.tertiary, CardDefaults.shape)
                } else {
                    Modifier
                }
            )

    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = onGroupItemClick,
                    onLongClick = onEditGroupClick
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 12.dp,
                        horizontal = 8.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Count: " + "4",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
        if (isDeleteActive) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = deleteIconPadding, y = -deleteIconPadding),
                contentAlignment = Alignment.TopEnd
            ) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.error)
                        .padding(2.dp)
                        .clickable(
                            onClick = onDeleteGroupClick
                        ),
                    imageVector = Icons.Default.Clear,
                    tint = MaterialTheme.colorScheme.onError,
                    contentDescription = "delete group icon"
                )
            }
        }
    }
}

@Preview
@Composable
private fun CategoryItemPreview() {
   MaterialTheme {
       CategoryItem(
           title = "category",
           onGroupItemClick = {},
           onEditGroupClick = {},
           onDeleteGroupClick = {},
       )
   }
}