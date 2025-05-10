package hu.tb.core.data.network.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val meals: List<CategoryDto>
)

@Serializable
data class CategoryDto(
    val strCategory: String
)
