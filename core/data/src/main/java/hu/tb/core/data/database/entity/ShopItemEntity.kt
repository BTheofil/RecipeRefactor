package hu.tb.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.tb.core.domain.shop.ShopItem

@Entity
data class ShopItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val name: String,
    val isChecked: Boolean
)

fun ShopItemEntity.toDomain(): ShopItem =
    ShopItem(
        id = id,
        name = name,
        isChecked = isChecked
    )

fun ShopItem.toEntity(): ShopItemEntity =
    ShopItemEntity(
        id = id,
        name = name,
        isChecked = isChecked
    )
