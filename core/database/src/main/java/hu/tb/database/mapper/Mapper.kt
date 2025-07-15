package hu.tb.database.mapper

import hu.tb.core.domain.meal.Category
import hu.tb.core.domain.shopping.ShoppingItem
import hu.tb.database.entity.CategoryEntity
import hu.tb.database.entity.ShoppingItemEntity

/**
 * Shopping
 **/
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

/**
 * Category
 **/
fun CategoryEntity.toDomain(): Category =
    Category(
        name = categoryName
    )

fun Category.toEntity(): CategoryEntity =
    CategoryEntity(
        categoryName = name
    )
