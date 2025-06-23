package hu.tb.database.mapper

import hu.tb.core.domain.meal.Category
import hu.tb.core.domain.meal.Food
import hu.tb.database.entity.ShoppingItemEntity
import hu.tb.core.domain.shopping.ShoppingItem
import hu.tb.database.entity.CategoryEntity
import hu.tb.database.entity.FoodEntity

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

/**
 * Food
 **/
fun FoodEntity.toDomain(): Food =
    Food(
        name = foodName,
        category = categoryEntity.toDomain(),
        quantity = quantity
    )

fun Food.toEntity(): FoodEntity =
    FoodEntity(
        foodName = name,
        categoryEntity = category.toEntity(),
        quantity = quantity
    )