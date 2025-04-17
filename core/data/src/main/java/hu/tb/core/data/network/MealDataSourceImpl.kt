package hu.tb.core.data.network

import hu.tb.core.domain.meal.MealDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class MealDataSourceImpl(
    private val httpClient: HttpClient
) : MealDataSource {

    override suspend fun getRandomMeal() {
        httpClient.get {
            url("$BASE_URL/random.php")
        }.body<MealDto>()
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

    companion object {
        private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1"
    }
}