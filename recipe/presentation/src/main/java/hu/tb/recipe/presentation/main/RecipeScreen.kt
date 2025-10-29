package hu.tb.recipe.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.tb.presentation.components.EmptyScreen
import hu.tb.presentation.theme.AppTheme
import hu.tb.presentation.theme.Icon
import hu.tb.recipe.presentation.components.RecipeItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecipeScreen(
    viewModel: RecipeViewModel = koinViewModel(),
    createRecipeScreenRequest: () -> Unit,
    detailRecipeScreenRequest: (recipeId: Long) -> Unit
) {
    RecipeScreen(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onAction = {
            when (it) {
                is RecipeAction.RecipeClick -> detailRecipeScreenRequest(it.recipeId)
                RecipeAction.CreateRecipeClick -> createRecipeScreenRequest()
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    state: RecipeState,
    onAction: (RecipeAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Recipes",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(RecipeAction.CreateRecipeClick) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    painterResource(Icon.add), "add recipe icon",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp),
        ) {
            if (state.recipes.isEmpty()) {
                EmptyScreen(
                    modifier = Modifier
                        .weight(1f),
                    text = "No recipes yet",
                    icon = Icon.book
                )
            } else {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth(),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(
                        items = state.recipes,
                        key = { it.id!! }
                    ) { recipe ->
                        RecipeItem(
                            modifier = Modifier
                                .clickable(
                                    onClick = { onAction(RecipeAction.RecipeClick(recipe.id!!)) },
                                ),
                            recipe = recipe
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun RecipeScreenPreview() {
    AppTheme {
        RecipeScreen(
            state = RecipeState(),
            onAction = {},
        )
    }
}