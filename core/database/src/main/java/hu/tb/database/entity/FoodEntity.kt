package hu.tb.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FoodEntity(
    @PrimaryKey(autoGenerate = true) val foodId: Long? = null,
    val foodName: String,
    @Embedded val categoryEntity: CategoryEntity,
    val quantity: Int
)
