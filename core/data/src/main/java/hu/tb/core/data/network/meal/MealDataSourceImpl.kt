package hu.tb.core.data.network.meal

import hu.tb.core.data.network.BASE_URL
import hu.tb.core.data.network.handleNetworkCall
import hu.tb.core.data.network.mapper.toDomain
import hu.tb.core.domain.meal.Meal
import hu.tb.core.domain.meal.MealDataSource
import hu.tb.core.domain.util.NetworkError
import hu.tb.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url

class MealDataSourceImpl(
    private val httpClient: HttpClient
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
        TODO("Not yet implemented")
    }

    override suspend fun getCategories() {
        TODO("Not yet implemented")
    }

    override suspend fun getMealByFilter(filter: String) {
        TODO("Not yet implemented")
    }
}