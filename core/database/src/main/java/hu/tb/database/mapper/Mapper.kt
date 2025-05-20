package hu.tb.database.mapper

import hu.tb.core.domain.meal.Category
import hu.tb.database.entity.ShoppingItemEntity
import hu.tb.core.domain.shopping.ShoppingItem
import hu.tb.database.entity.CategoryEntity

fun ShoppingItemEntity.toDomain(): ShoppingItem =
    ShoppingItem(
        id = id,
        name = name,
        isChecked = isChecked
    )

fun ShoppingItem.toEntity(): ShoppingItemEntity =
    ShoppingItemEntity(
        id = id,
        name = name,
        isChecked = isChecked
    )

//===Food===
fun CategoryEntity.toDomain(): Category =
    Category(
        name = name
    )

fun Category.toEntity(): CategoryEntity =
    CategoryEntity(
        name = name
    )