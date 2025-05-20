package hu.tb.recipe.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.tb.core.domain.meal.Category
import hu.tb.core.domain.meal.FoodRepository
import hu.tb.core.domain.meal.MealDataSource
import hu.tb.core.domain.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val mealDataSource: MealDataSource,
    private val foodRepository: FoodRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(isCategoriesLoading = true) }
            val categories = foodRepository.getAll()
            if (categories.isNotEmpty()) {
                _state.update {
                    it.copy(
                        categories = categories,
                        isCategoriesLoading = false
                    )
                }
            } else {
                getCategories()
                if (!state.value.isCategoryFailed)
                    saveCategories(state.value.categories)
            }
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isMealsLoading = true,
                    isFilterMealLoading = true
                )
            }
            getMeals()
            getFilterMeals()
        }
    }

    fun onAction(action: RecipeAction) =
        when (action) {
            is RecipeAction.OnSearch -> {}
            is RecipeAction.OnSearchTextChange ->
                _state.update {
                    it.copy(
                        searchField = action.text
                    )
                }

            is RecipeAction.OnFilterCategoryClick ->
                _state.update {
                    it.copy(
                        selectedCategoryIndex = action.index
                    )
                }
        }

    private suspend fun getMeals() {
        when (val result = mealDataSource.getRandomMeal()) {
            is Result.Error -> _state.update {
                it.copy(isErrorOccurred = true)
            }

            is Result.Success -> _state.update {
                it.copy(meals = result.data)
            }
        }
        _state.update { it.copy(isMealsLoading = false) }
    }

    private suspend fun getCategories() {
        when (val result = mealDataSource.getCategories()) {
            is Result.Error -> _state.update {
                it.copy(isCategoryFailed = true)
            }

            is Result.Success -> _state.update {
                it.copy(categories = result.data)
            }
        }
        _state.update { it.copy(isCategoriesLoading = false) }
    }

    private suspend fun saveCategories(categories: List<Category>) =
        categories.forEach {
            foodRepository.save(it)
        }


    private suspend fun getFilterMeals() {
        when (val result = mealDataSource.getMealByFilter(state.value.selectedFilter)) {
            is Result.Error -> _state.update {
                it.copy(isErrorOccurred = true)
            }

            is Result.Success -> _state.update {
                it.copy(filterMeals = result.data)
            }
        }
        _state.update { it.copy(isFilterMealLoading = false) }
    }
}