package hu.tb.presentation.storage

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.tb.core.domain.meal.Category
import hu.tb.presentation.components.CategoryItem
import hu.tb.presentation.components.PlusButton
import hu.tb.presentation.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun StorageScreen(
    viewModel: StorageViewModel = koinViewModel(),
    onCreationRequested: () -> Unit
) {
    StorageScreen(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        action = { action ->
            when (action) {
                StorageAction.OnAddFoodClick -> onCreationRequested()
                else -> viewModel::onAction
            }
        }
    )
}

private val DELETE_ICON_OFFSET_DP = 8.dp
private val GROUP_ITEM_DEFAULT_HEIGHT = 60.dp
private const val SHAKE_EFFECT_MIN = -2f
private const val SHAKE_EFFECT_MAX = 2f

@Composable
private fun StorageScreen(
    state: StorageState,
    action: (StorageAction) -> Unit
) {
    val density = LocalDensity.current
    var groupItemHeightDp by remember { mutableStateOf(GROUP_ITEM_DEFAULT_HEIGHT) }

    var isGroupDeleteActive by remember { mutableStateOf(false) }

    val groupItemShakeValue = remember { Animatable(SHAKE_EFFECT_MIN) }

    LaunchedEffect(isGroupDeleteActive) {
        if (isGroupDeleteActive) {
            groupItemShakeValue.animateTo(
                targetValue = SHAKE_EFFECT_MAX,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
            )
        }
    }

    val scroll = rememberLazyGridState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Spacer(Modifier.height(64.dp))
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 1f, fill = false)
                    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                    .drawWithContent {
                        drawContent()
                        if (scroll.canScrollForward) {
                            drawRect(
                                brush = Brush.verticalGradient(
                                    0.9f to Color.White,
                                    1f to Color.Transparent,
                                ),
                                blendMode = BlendMode.DstIn
                            )
                        }
                        if(scroll.canScrollBackward) {
                            drawRect(
                                brush = Brush.verticalGradient(
                                    0f to Color.Transparent,
                                    0.1f to Color.White,
                                ),
                                blendMode = BlendMode.DstIn
                            )
                        }
                    },
                state = scroll,
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
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
                        title = item.name,
                        count = 4,
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
                    PlusButton(
                        modifier = Modifier
                            .padding(top = DELETE_ICON_OFFSET_DP)
                            .size(groupItemHeightDp - DELETE_ICON_OFFSET_DP),
                        onClick = { action(StorageAction.OnAddCategoryClick) }
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Products",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
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
                item {
                    PlusButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = { action(StorageAction.OnAddFoodClick) }
                    )
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
            Category("meal"),
            Category("fruit"),
            Category("milk"),
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