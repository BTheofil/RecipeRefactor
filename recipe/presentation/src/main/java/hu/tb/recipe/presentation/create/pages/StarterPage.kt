package hu.tb.recipe.presentation.create.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.presentation.theme.AppTheme
import hu.tb.recipe.presentation.components.NextPageButton
import hu.tb.recipe.presentation.create.CreateAction
import hu.tb.recipe.presentation.create.CreationState

@Composable
fun StarterPage(
    state: CreationState,
    onAction: (CreateAction.StarterAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = state.recipeName,
            onValueChange = { onAction(CreateAction.StarterAction.RecipeNameChange(it)) },
            label = {
                Text(
                    text = "Recipe name",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            isError = state.isRecipeNameHasError
        )
        Spacer(Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            NextPageButton(
                onClick = { onAction(CreateAction.StarterAction.OnNextPage) }
            )
        }
    }
}

@Preview
@Composable
private fun StarterPagePreview() {
    AppTheme {
        StarterPage(
            state = CreationState(),
            onAction = {}
        )
    }
}