package hu.tb.recipe.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.presentation.theme.AppTheme

@Composable
fun RecipeScreen() {
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    state: RecipeState,
    onAction: (RecipeAction) -> Unit
) {
    var isSearchExpanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .padding(horizontal = 16.dp),
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth(),
            inputField = {
                SearchBarDefaults.InputField(
                    query = state.searchField,
                    onQueryChange = { RecipeAction.OnSearchTextChange(it) },
                    onSearch = {
                        RecipeAction.OnSearch(it)
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
            content = {},
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        )

    }
}

@Preview
@Composable
private fun RecipeScreenPreview() {
    AppTheme {
        RecipeScreen(
            state = RecipeState(),
            onAction = {}
        )
    }
}