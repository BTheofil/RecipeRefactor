package hu.tb.recipe.presentation.create.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.presentation.components.clearFocus
import hu.tb.presentation.theme.AppTheme
import hu.tb.recipe.presentation.components.StepEditItem
import hu.tb.recipe.presentation.create.CreateAction
import hu.tb.recipe.presentation.create.CreationState

@Composable
fun StepsPage(
    state: CreationState,
    onAction: (CreateAction.StepsAction) -> Unit
) {
    var isStepsAreEmpty by remember { mutableStateOf(false) }

    LaunchedEffect(state.isStepsHasError) {
        isStepsAreEmpty = state.isStepsHasError
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clearFocus(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Describe how to make it",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))
        AnimatedVisibility(
            visible = isStepsAreEmpty,
            enter = scaleIn(),
            exit = scaleOut() + fadeOut()
        ) {
            Text(
                text = "Please fill or remove the empty step",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            itemsIndexed(
                items = state.steps,
                key = { index, _ -> index }
            ) { index, item ->
                StepEditItem(
                    modifier = Modifier
                        .animateItem(),
                    index = index,
                    text = item,
                    onTextChange = {
                        onAction(
                            CreateAction.StepsAction.StepFieldChange(
                                index = index,
                                text = it
                            )
                        )
                    },
                    isRemoveIconVisible = state.steps.size > 1,
                    onRemoveClick = {
                        onAction(CreateAction.StepsAction.RemoveStep(index = index))
                    }
                )
            }
            item {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    content = {
                        TextButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = { onAction(CreateAction.StepsAction.AddStepField) }
                        ) {
                            Text(
                                "Add step",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                )
            }
        }
        Button(
            onClick = { onAction(CreateAction.StepsAction.FinishSteps) },
            content = {
                Text(
                    text = "Done",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        )
    }
}

@Preview
@Composable
private fun StepsPagePreview() {
    AppTheme {
        StepsPage(
            state = CreationState(),
            onAction = {}
        )
    }
}