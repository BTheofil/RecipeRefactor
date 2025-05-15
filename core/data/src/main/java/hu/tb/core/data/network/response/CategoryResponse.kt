package hu.tb.core.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("meals")
    val categories: List<CategoryDto>
)

@Serializable
data class CategoryDto(
    val strCategory: String
)
