package hu.tb.recipe.presentation.create

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.tb.presentation.theme.AppTheme
import hu.tb.recipe.presentation.create.pages.IngredientsPage
import hu.tb.recipe.presentation.create.pages.StarterPage
import hu.tb.recipe.presentation.create.pages.StepsPage
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateScreen(
    viewModel: CreateViewModel = koinViewModel()
) {
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

    val scope = rememberCoroutineScope()

    BackHandler(
        enabled = pagerState.currentPage != 0
    ) {
        scope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage - 1)
        }
    }

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

@Preview
@Composable
private fun CreateScreenPreview() {
    AppTheme {
        CreateScreen()
    }
}