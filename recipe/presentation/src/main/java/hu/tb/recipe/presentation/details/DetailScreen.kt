package hu.tb.recipe.presentation.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.zIndex
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
                    delay(SnackbarVisibleTime)
                    isRecipeAddedSnackbarVisible = false
                }
            }
        }
    }

    DetailScreen(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        makeRecipeClick = { viewModel.checkIngredientsAndMakeIt() },
        isSnackbarVisible = isRecipeAddedSnackbarVisible
    )
}

private val SnackbarVisibleTime = 4.seconds
private const val CHECK_ANIMATION_DURATION_MILLIS = 400

@Composable
private fun DetailScreen(
    state: DetailState,
    makeRecipeClick: () -> Unit,
    isSnackbarVisible: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                    ) {
                        Text(
                            text = "Ingredients",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(16.dp))
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            state.recipe.ingredients.forEachIndexed { index, ingredient ->
                                IngredientItem(
                                    ingredient = ingredient,
                                    availability = state.recipeIngredientsResult.find { it.product == ingredient }?.availability
                                        ?: Availability.INIT,
                                    calculatedDelay = CHECK_ANIMATION_DURATION_MILLIS * index,
                                    isChecking = state.isChecking
                                )
                            }
                        }
                    }
                }
            )
            Spacer(Modifier.height(12.dp))
            SectionBackground(
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = "How to",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(16.dp))
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            state.recipe.howToMakeSteps.forEachIndexed { index, step ->
                                NumberedStep(
                                    index = index + 1,
                                    description = step.description
                                )
                            }
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            IngredientCheckButton(
                isRecipeCookable = state.isRecipeCookable,
                isCheckButtonEnabled = !isSnackbarVisible,
                onButtonClick = { makeRecipeClick() }
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(Modifier.weight(1f))
        Snackbar(
            isSnackbarVisible = isSnackbarVisible,
            recipeName = state.recipe?.name
        )
    }
}

@Composable
private fun IngredientCheckButton(
    modifier: Modifier = Modifier,
    isRecipeCookable: Boolean,
    isCheckButtonEnabled: Boolean,
    onButtonClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
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
            if (checkState && isRecipeCookable) {
                HoldingButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onComplete = {
                        onButtonClick()
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
                        onButtonClick()
                        isCheckedIngredients = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    enabled = isCheckButtonEnabled,
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

@Composable
private fun Snackbar(
    isSnackbarVisible: Boolean,
    recipeName: String?
) {
    AnimatedVisibility(
        modifier = Modifier
            .zIndex(1f),
        visible = isSnackbarVisible,
        enter = fadeIn() + slideInVertically(
            initialOffsetY = { it / 2 },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            )
        ),
        exit = fadeOut() + slideOutVertically(
            targetOffsetY = { it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            recipeName?.let {
                CustomSnackbar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "$it is added to your storage",
                    progressDuration = SnackbarVisibleTime
                )
            }
        }
    }
}

@Composable
private fun IngredientItem(
    ingredient: Product,
    availability: Availability,
    calculatedDelay: Int = 0,
    isChecking: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isChecking) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
        } else {
            AnimatedContent(
                targetState = availability,
                transitionSpec = {
                    fadeIn(tween(delayMillis = calculatedDelay)).togetherWith(fadeOut())
                }
            ) { state ->
                val icon = when (state) {
                    Availability.HAVE -> Icon.check
                    Availability.LESS -> Icon.close
                    Availability.UNKNOWN -> Icon.question_mark
                    Availability.INIT -> null
                }
                icon?.let {
                    Icon(
                        modifier = Modifier,
                        painter = painterResource(it),
                        contentDescription = "availability icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                } ?: Spacer(Modifier.width(24.dp))
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
                if (animatedAvailability != Availability.INIT) {
                    Text(
                        text = when (availability) {
                            Availability.HAVE -> "Have enough"
                            Availability.LESS -> "Less than needed"
                            Availability.UNKNOWN -> "Not recorded"
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
    val isSingleLine by remember { derivedStateOf { stepLineCount == 1 } }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = if (isSingleLine) Alignment.CenterVertically else Alignment.Top,
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
        ),
    )

    AppTheme {
        DetailScreen(
            state = DetailState(recipe = mockRecipe, isChecking = true),
            makeRecipeClick = {},
            isSnackbarVisible = false
        )
    }
}