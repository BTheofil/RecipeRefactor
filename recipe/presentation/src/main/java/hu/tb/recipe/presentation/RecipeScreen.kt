package hu.tb.recipe.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import hu.tb.core.domain.meal.FilterMeal
import hu.tb.presentation.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecipeScreen(
    viewModel: RecipeViewModel = koinViewModel(),
) {
    RecipeScreen(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onAction = viewModel::onAction,
        showMoreMealClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    state: RecipeState,
    onAction: (RecipeAction) -> Unit,
    showMoreMealClick: () -> Unit
) {
    var isSearchExpanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth(),
            inputField = {
                SearchBarDefaults.InputField(
                    query = state.searchField,
                    onQueryChange = { onAction(RecipeAction.OnSearchTextChange(it)) },
                    onSearch = {
                        onAction(RecipeAction.OnSearch(it))
                        isSearchExpanded = false
                    },
                    onExpandedChange = { isSearchExpanded = it },
                    expanded = isSearchExpanded,
                    placeholder = {
                        Text(
                            text = "Search meal...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                )
            },
            expanded = isSearchExpanded,
            onExpandedChange = { isSearchExpanded = it },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            content = {},
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Recommended meals",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .size(128.dp)
                    .border(
                        4.dp,
                        MaterialTheme.colorScheme.secondary,
                        RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = state.isMealsLoading
                ) { isLoading ->
                    if (isLoading) {
                        CircularProgressIndicator()
                    } else {
                        AsyncImage(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp)),
                            model = state.meals.first().image,
                            contentDescription = "meal image",
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(24.dp))
        AnimatedContent(
            targetState = state.isCategoriesLoading
        ) { isLoading ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (isLoading) {
                    repeat(5) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                } else {
                    state.categories.forEach { category ->
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                        ) {
                            Text(
                                text = category.name
                            )
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        AnimatedContent(
            targetState = state.isFilterMealLoading
        ) { isLoading ->
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Column {
                    state.filterMeals.chunked(2).take(2).forEach { meals ->
                        Row {
                            meals.forEach { meal ->
                                FilterMealCard(
                                    modifier = Modifier
                                        .weight(1f),
                                    meal = meal
                                )
                            }
                        }
                    }
                    TextButton(
                        modifier = Modifier,
                        onClick = showMoreMealClick
                    ) {
                        Text("show")
                    }
                }
            }
        }
        Spacer(Modifier.height(28.dp))
        Text(
            text = "Your recipes",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun FilterMealCard(
    modifier: Modifier = Modifier,
    meal: FilterMeal
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(
                4.dp, MaterialTheme.colorScheme.secondary,
                RoundedCornerShape(16.dp)
            )
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
                .clip(RoundedCornerShape(16.dp)),
            model = meal.image,
            contentScale = ContentScale.FillWidth,
            contentDescription = "filter meal image"
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = meal.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun RecipeScreenPreview() {
    AppTheme {
        RecipeScreen(
            state = RecipeState(
                isCategoriesLoading = true,
                isMealsLoading = true,
                isFilterMealLoading = true
            ),
            onAction = {},
            showMoreMealClick = {}
        )
    }
}