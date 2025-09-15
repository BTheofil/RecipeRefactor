package hu.tb.recipe.presentation.create.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.presentation.theme.AppTheme
import hu.tb.recipe.presentation.create.CreateAction

@Composable
fun StepsPage(
    stepList: List<String>,
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            stepList.forEachIndexed { index, step ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        value = step,
                        onValueChange = {
                            onAction(
                                CreateAction.StepsAction.StepFieldChange(
                                    index = index,
                                    text = it
                                )
                            )
                        },
                        decorationBox = { innerTextField ->
                            if (step.isEmpty()) {
                                Text("write your recipe steps...")
                            }
                            innerTextField()
                        }
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
            OutlinedButton(
                onClick = { onAction(CreateAction.StepsAction.AddStepField) },
                content = {
                    Text(
                        text = "Add step",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            )
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
            stepList = listOf(""),
            onAction = {}
        )
    }
}