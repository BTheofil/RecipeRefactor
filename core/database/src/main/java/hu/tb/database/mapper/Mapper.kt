package hu.tb.database.mapper

import hu.tb.database.entity.ShoppingItemEntity
import hu.tb.domain.ShoppingItem

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