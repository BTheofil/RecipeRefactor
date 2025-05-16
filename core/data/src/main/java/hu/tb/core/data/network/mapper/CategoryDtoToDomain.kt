package hu.tb.core.data.network.mapper

import hu.tb.core.data.network.response.CategoryDto
import hu.tb.core.domain.meal.Category

fun CategoryDto.toDomain(): Category = Category(name = strCategory)