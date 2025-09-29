package hu.tb.recipe.presentation.create.pages

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.presentation.theme.AppTheme
import hu.tb.recipe.presentation.create.CreateAction
import hu.tb.recipe.presentation.create.CreationState

@Composable
fun StepsPage(
    state: CreationState,
    onAction: (CreateAction.StepsAction) -> Unit
) {
    val focusManger = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = { focusManger.clearFocus() }
            )
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .scrollable(
                    state = rememberScrollState(),
                    orientation = Orientation.Vertical
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.steps.forEachIndexed { index, stepText ->
                var nextStepState by remember { mutableStateOf(StepItemState.BUTTON) }
                AnimatedContent(
                    targetState = nextStepState,
                    transitionSpec = {
                        ContentTransform(
                            targetContentEnter = scaleIn(),
                            initialContentExit = scaleOut()
                        )
                    }
                ) { itemState ->
                    when (itemState) {
                        StepItemState.BUTTON -> ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            content = {
                                TextButton(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    onClick = {
                                        nextStepState = StepItemState.EDIT
                                        onAction(CreateAction.StepsAction.AddStepField)
                                    }
                                ) {
                                    Text(
                                        "Add step",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        )

                        StepItemState.EDIT -> {
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = CardDefaults.elevatedCardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                ),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(18.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = (index + 1).toString(),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                    Spacer(Modifier.width(16.dp))
                                    BasicTextField(
                                        modifier = Modifier
                                            .weight(1f),
                                        value = stepText,
                                        onValueChange = {
                                            onAction(
                                                CreateAction.StepsAction.StepFieldChange(
                                                    index = index,
                                                    text = it
                                                )
                                            )
                                        },
                                        decorationBox = { innerTextField ->
                                            if (stepText.isEmpty()) {
                                                Text("write your recipe steps...")
                                            }
                                            innerTextField()
                                        }
                                    )
                                    IconButton(
                                        onClick = {
                                            nextStepState = StepItemState.BUTTON
                                            onAction(CreateAction.StepsAction.RemoveStep(index = index))
                                        }
                                    ) {
                                        Icon(
                                            Icons.Rounded.Close,
                                            "close icon",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
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

private enum class StepItemState {
    BUTTON, EDIT
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