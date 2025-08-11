package hu.tb.recipe.presentation.create.pages

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.tb.presentation.theme.AppTheme
import hu.tb.recipe.presentation.create.CreateAction

@Composable
fun StarterPage(
    recipeName: String,
    onAction: (CreateAction.StarterAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(rememberScrollState(), orientation = Orientation.Horizontal)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = recipeName,
            onValueChange = { onAction(CreateAction.StarterAction.RecipeNameChange(it)) },
            label = {
                Text(
                    text = "Recipe name",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        )
        Spacer(Modifier.weight(1f))
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { onAction(CreateAction.StarterAction.OnNextPage) }
        ) {
            Text(
                text = "Next page",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
private fun StarterPagePreview() {
    var mockName by remember {
        mutableStateOf("")
    }

    AppTheme {
        StarterPage(
            recipeName = mockName,
            onAction = {
                when (it) {
                    CreateAction.StarterAction.OnNextPage -> {}
                    is CreateAction.StarterAction.RecipeNameChange -> {
                        mockName = it.name
                    }
                }
            }
        )
    }
}