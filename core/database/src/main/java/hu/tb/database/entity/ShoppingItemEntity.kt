package hu.tb.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val name: String,
    val isChecked: Boolean
)
