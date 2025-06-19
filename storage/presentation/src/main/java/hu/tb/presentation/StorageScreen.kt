package hu.tb.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.presentation.components.CategoryItem
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
                    CategoryItem(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                groupItemHeightDp = with(density) { coordinates.size.height.toDp() }
                            }
                            .graphicsLayer {
                                translationX = groupItemShakeValue.value
                            },
                        deleteIconPadding = DELETE_ICON_OFFSET_DP,
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
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