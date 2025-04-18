package hu.tb.core.data.network

import hu.tb.core.data.network.mapper.toDomain
import hu.tb.core.domain.meal.Meal
import hu.tb.core.domain.meal.MealDataSource
import hu.tb.core.domain.util.NetworkError
import hu.tb.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

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

    private suspend inline fun <reified T> handleNetworkCall(
        execute: () -> HttpResponse
    ): Result<T, NetworkError> {
        val response = try {
            execute()
        } catch (e: UnresolvedAddressException) {
            e.printStackTrace()
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            e.printStackTrace()
            return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(NetworkError.UNKNOWN)
        }

        return respondToResult(response)
    }

    private suspend inline fun <reified T> respondToResult(response: HttpResponse): Result<T, NetworkError> =
        when (response.status.value) {
            in 200..299 -> Result.Success(response.body<T>())
            else -> Result.Error(NetworkError.HTTP_ERROR)
        }

    companion object {
        private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1"
    }
}