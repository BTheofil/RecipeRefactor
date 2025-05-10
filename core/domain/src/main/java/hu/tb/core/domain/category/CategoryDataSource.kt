package hu.tb.core.domain.category

import hu.tb.core.domain.util.NetworkError
import hu.tb.core.domain.util.Result

interface CategoryDataSource {

    suspend fun getCategories(): Result<List<Category>, NetworkError>
}