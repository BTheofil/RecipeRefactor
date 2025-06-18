package hu.tb.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.presentation.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

private val DELETE_ICON_OFFSET_DP = 8.dp

@Composable
fun StorageScreen(
    viewModel: StorageViewModel = koinViewModel()
) {
    StorageScreen(
        state = StorageState(),
        action = viewModel::onAction
    )
}

@Composable
private fun StorageScreen(
    state: StorageState,
    action: (StorageAction) -> Unit
) {
    val density = LocalDensity.current
    var groupItemHeightDp by remember { mutableStateOf(0.dp) }

    var isGroupDeleteActive by remember { mutableStateOf(false) }

    val groupItemShakeValue = remember { Animatable(-2f) }

    LaunchedEffect(isGroupDeleteActive) {
        if (isGroupDeleteActive) {
            groupItemShakeValue.animateTo(
                targetValue = 2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
            )
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Spacer(Modifier.height(64.dp))
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(
                    items = state.categories
                ) { index, item ->
                    GroupItem(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                groupItemHeightDp = with(density) { coordinates.size.height.toDp() }
                            }
                            .graphicsLayer {
                                translationX = groupItemShakeValue.value
                            },
                        title = item,
                        onGroupItemClick = { action(StorageAction.OnCategoryClick) },
                        onEditGroupClick = {
                            isGroupDeleteActive = !isGroupDeleteActive
                        },
                        onDeleteGroupClick = { action(StorageAction.OnDeleteCategoryClick) },
                        isDeleteActive = isGroupDeleteActive,
                        isGroupSelected = state.selectedGroupIndex == index
                    )
                }
                item {
                    IconButton(
                        modifier = Modifier
                            .padding(top = DELETE_ICON_OFFSET_DP)
                            .size(groupItemHeightDp - DELETE_ICON_OFFSET_DP)
                            .border(
                                1.dp,
                                Color.Black.copy(alpha = 0.3f),
                                RoundedCornerShape(16.dp)
                            ),
                        onClick = { action(StorageAction.OnAddCategoryClick) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "Create new group icon"
                        )
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = state.foods
                ) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row {
                            Text(
                                text = item
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GroupItem(
    modifier: Modifier = Modifier,
    title: String,
    onGroupItemClick: () -> Unit,
    onEditGroupClick: () -> Unit,
    onDeleteGroupClick: () -> Unit,
    isDeleteActive: Boolean = false,
    isGroupSelected: Boolean = false
) {
    Box(
        modifier = modifier
            .padding(top = DELETE_ICON_OFFSET_DP)
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
                    .offset(x = DELETE_ICON_OFFSET_DP, y = -DELETE_ICON_OFFSET_DP),
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
private fun StorageScreenPreview() {

    val mockState = StorageState(
        categories = listOf(
            "meet", "fruit", "milk"
        ),
        foods = listOf(
            "apple", "banana", "lemon"
        )
    )


    AppTheme {
        StorageScreen(
            state = mockState,
            action = {}
        )
    }
}