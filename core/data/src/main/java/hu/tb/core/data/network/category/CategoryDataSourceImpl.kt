package hu.tb.core.data.network.category

import hu.tb.core.data.network.BASE_URL
import hu.tb.core.data.network.handleNetworkCall
import hu.tb.core.data.network.mapper.toDomain
import hu.tb.core.domain.category.Category
import hu.tb.core.domain.category.CategoryDataSource
import hu.tb.core.domain.util.NetworkError
import hu.tb.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url

class CategoryDataSourceImpl(
    private val httpClient: HttpClient
) : CategoryDataSource {

    override suspend fun getCategories(): Result<List<Category>, NetworkError> {
        val response = handleNetworkCall<CategoryResponse> {
            httpClient.get { url("$BASE_URL/list.php?c=list") }
        }

        return when (response) {
            is Result.Success -> Result.Success(response.data.meals.map { it.toDomain() })
            is Result.Error -> Result.Error(response.error)
        }
    }
}