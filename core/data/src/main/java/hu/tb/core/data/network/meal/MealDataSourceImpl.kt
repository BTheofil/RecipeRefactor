package hu.tb.core.data.network.meal

import hu.tb.core.data.network.BASE_URL
import hu.tb.core.data.network.response.CategoryResponse
import hu.tb.core.data.network.handleNetworkCall
import hu.tb.core.data.network.mapper.toDomain
import hu.tb.core.data.network.response.MealFilterResponse
import hu.tb.core.data.network.response.MealResponse
import hu.tb.core.domain.meal.Category
import hu.tb.core.domain.meal.FilterMeal
import hu.tb.core.domain.meal.FilterMealState
import hu.tb.core.domain.meal.Meal
import hu.tb.core.domain.meal.MealDataSource
import hu.tb.core.domain.util.NetworkError
import hu.tb.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MealDataSourceImpl(
    private val httpClient: HttpClient,
) : MealDataSource {

    override suspend fun getRandomMeal(): Result<List<Meal>, NetworkError> {
        val response = handleNetworkCall<MealResponse> {
            httpClient.get { url("$BASE_URL/random.php") }
        }

        return when (response) {
            is Result.Success -> Result.Success(response.data.meals.map { it.toDomain() })
            is Result.Error -> Result.Error(response.error)
        }
    }

    override suspend fun getMealById(id: Long) {
        TODO()
    }

    override suspend fun getCategories(): Result<List<Category>, NetworkError> {
        val response = handleNetworkCall<CategoryResponse> {
            httpClient.get { url("$BASE_URL/list.php?c=list") }
        }

        return when (response) {
            is Result.Success -> Result.Success(response.data.categories.map { it.toDomain() })
            is Result.Error -> Result.Error(response.error)
        }
    }

    override suspend fun getMealByFilter(category: Category): Result<List<FilterMeal>, NetworkError> {
        val response = handleNetworkCall<MealFilterResponse> {
            httpClient.get { url("$BASE_URL/filter.php?c=${category.name}") }
        }

        return when (response) {
            is Result.Success -> {
                filterState.update {
                    it.copy(
                        category = category,
                        filterMealList = response.data.filteredMeals.map { it.toDomain() }
                    )
                }
                Result.Success(response.data.filteredMeals.map { it.toDomain() })
            }

            is Result.Error -> Result.Error(response.error)
        }
    }

    private val filterState = MutableStateFlow(FilterMealState())
    override val filterMeals: StateFlow<FilterMealState>
        get() = filterState.asStateFlow()
}