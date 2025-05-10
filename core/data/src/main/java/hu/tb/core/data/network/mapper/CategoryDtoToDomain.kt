package hu.tb.core.data.network.mapper

import hu.tb.core.data.network.category.CategoryDto
import hu.tb.core.domain.category.Category

fun CategoryDto.toDomain(): Category = Category(name = strCategory)