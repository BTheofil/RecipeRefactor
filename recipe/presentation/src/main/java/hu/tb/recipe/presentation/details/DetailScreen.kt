package hu.tb.recipe.presentation.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.product.Product
import hu.tb.core.domain.recipe.Recipe
import hu.tb.core.domain.recipe.details.Availability
import hu.tb.core.domain.step.Step
import hu.tb.presentation.theme.AppTheme
import hu.tb.presentation.theme.Icon
import hu.tb.recipe.presentation.components.CustomSnackbar
import hu.tb.recipe.presentation.components.HoldingButton
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import kotlin.time.Duration.Companion.seconds

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = koinViewModel()
) {
    var isRecipeAddedSnackbarVisible by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.event, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.event.collect { event ->
                if (event is DetailEvent.RecipeAddedToDepo) {
                    isRecipeAddedSnackbarVisible = true
                    delay(4.seconds)
                    isRecipeAddedSnackbarVisible = false
                }
            }
        }
    }

    DetailScreen(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        makeRecipeClick = { viewModel.checkIngredientsAndMakeIt() }
    )

    AnimatedVisibility(
        visible = isRecipeAddedSnackbarVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 8.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            viewModel.state.value.recipe?.let {
                CustomSnackbar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = it.name + " is added to your storage"
                )
            }
        }
    }
}

private const val CHECK_ANIMATION_DURATION_MILLIS = 400

@Composable
private fun DetailScreen(
    state: DetailState,
    makeRecipeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .scrollable(rememberScrollState(), Orientation.Horizontal)
    ) {
        if (state.recipe == null) {
            CircularProgressIndicator()
        } else {
            SectionBackground(
                content = {
                    Text(
                        text = state.recipe.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
            Spacer(Modifier.height(16.dp))
            SectionBackground(
                content = {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(
                            items = state.recipe.ingredients,
                            key = { _, item -> item.id!! }
                        ) { index, ingredient ->
                            IngredientItem(
                                ingredient = ingredient,
                                availability = state.recipeIngredientsResult.find { it.product == ingredient }?.availability
                                    ?: Availability.UNKNOWN,
                                calculatedDelay = CHECK_ANIMATION_DURATION_MILLIS * index
                            )
                        }
                    }
                }
            )
            Spacer(Modifier.height(12.dp))
            SectionBackground(
                content = {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(
                            items = state.recipe.howToMakeSteps,
                            key = { _, item -> item.id!! }
                        ) { index, step ->
                            NumberedStep(
                                index = index + 1,
                                description = step.description
                            )
                        }
                    }
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                var isCheckedIngredients by remember { mutableStateOf(false) }
                AnimatedContent(
                    targetState = isCheckedIngredients,
                    transitionSpec = {
                        fadeIn()
                            .togetherWith(
                                fadeOut()
                            )
                    }
                ) { checkState ->
                    if (checkState && state.isRecipeCookable) {
                        HoldingButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onComplete = {
                                makeRecipeClick()
                                isCheckedIngredients = false
                            },
                            content = {
                                Text(
                                    text = "Hold to make this recipe",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            },
                            coverColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                        )
                    } else {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {
                                makeRecipeClick()
                                isCheckedIngredients = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            content = {
                                Text(
                                    text = "Check ingredients",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun IngredientItem(
    ingredient: Product,
    availability: Availability?,
    calculatedDelay: Int = 0
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedContent(
            targetState = availability,
            transitionSpec = {
                fadeIn(tween(delayMillis = calculatedDelay)).togetherWith(fadeOut())
            }
        ) { state ->
            val icon = when (state) {
                Availability.HAVE -> Icon.check
                Availability.LESS -> Icon.close
                else -> null
            }
            if (icon != null) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(icon),
                    contentDescription = "availability icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(Modifier.width(12.dp))
        AnimatedContent(
            targetState = availability,
            transitionSpec = {
                fadeIn(tween(delayMillis = calculatedDelay)).togetherWith(fadeOut())
            }
        ) { animatedAvailability ->
            Column {
                Text(
                    text = ingredient.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
                if (animatedAvailability != null && animatedAvailability != Availability.UNKNOWN) {
                    Text(
                        text = when (availability) {
                            Availability.HAVE -> "Have enough"
                            Availability.LESS -> "Less than needed"
                            else -> ""
                        },
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
        Spacer(Modifier.weight(1f))
        Text(
            text = ingredient.quantity.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = ingredient.measure.toDisplay,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
private fun NumberedStep(
    index: Int,
    description: String,
) {
    var stepLineCount by remember { mutableIntStateOf(-1) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = if (stepLineCount == 1) Alignment.CenterVertically else Alignment.Top,
    ) {
        Text(
            text = "$index.",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(8.dp))
        Text(
            modifier = Modifier
                .height(IntrinsicSize.Max),
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            onTextLayout = {
                stepLineCount = it.lineCount
            }
        )
    }
}

@Composable
private fun SectionBackground(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview
@Composable
private fun DetailScreenPreview() {
    val mockRecipe = Recipe(
        id = 1,
        name = "First meal",
        ingredients = listOf(
            Product(id = 1, name = "apple", quantity = 1.0, measure = Measure.PIECE),
            Product(id = 2, name = "apple", quantity = 1.0, measure = Measure.PIECE),
            Product(id = 3, name = "apple", quantity = 1.0, measure = Measure.PIECE),
        ),
        howToMakeSteps = listOf(
            Step(id = 1, description = "cut"),
            Step(id = 2, description = "bake")
        )
    )

    AppTheme {
        DetailScreen(state = DetailState(recipe = mockRecipe), makeRecipeClick = {})
    }
}