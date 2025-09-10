package hu.tb.recipe.presentation.create

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import hu.tb.presentation.theme.AppTheme
import hu.tb.recipe.presentation.create.pages.IngredientsPage
import hu.tb.recipe.presentation.create.pages.StarterPage
import hu.tb.recipe.presentation.create.pages.StepsPage
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateScreen(
    viewModel: CreateViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(viewModel.event, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.event.collect { event ->
                if (event is CreationEvent.RecipeSaved) {
                    navigateBack()
                }
            }
        }
    }

    CreateScreen(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onAction = viewModel::onAction
    )
}

@Composable
private fun CreateScreen(
    state: CreationState,
    onAction: (CreateAction) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 3 }
    )
    var progress by remember { mutableFloatStateOf(0.1f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
    )

    val scope = rememberCoroutineScope()

    BackHandler(
        enabled = pagerState.currentPage != 0
    ) {
        scope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage - 1)
        }
    }

    LaunchedEffect(pagerState.settledPage) {
        when (pagerState.settledPage) {
            0 -> progress = 0.1f
            1 -> progress = 0.5f
            2 -> progress = 0.9f
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(8.dp))
        LinearProgressIndicator(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
                .height(16.dp),
            progress = { animatedProgress }
        )
        HorizontalPager(
            state = pagerState,
            pageContent = { index ->
                when (index) {
                    0 -> StarterPage(
                        recipeName = state.recipeName,
                        onAction = {
                            when (it) {
                                is CreateAction.StarterAction.OnNextPage -> scope.launch {
                                    pagerState.animateScrollToPage(index + 1)
                                }

                                else -> onAction(it)
                            }
                        }
                    )

                    1 -> IngredientsPage(
                        ingredients = state.ingredients,
                        onAction = {
                            when (it) {
                                is CreateAction.IngredientsAction.OnNextPage -> scope.launch {
                                    pagerState.animateScrollToPage(index + 1)
                                }

                                else -> onAction(it)
                            }
                        }
                    )

                    2 -> StepsPage(
                        stepList = state.steps,
                        onAction = { onAction(it) }
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun CreateScreenPreview() {
    AppTheme {
        CreateScreen(
            state = CreationState(),
            onAction = {}
        )
    }
}