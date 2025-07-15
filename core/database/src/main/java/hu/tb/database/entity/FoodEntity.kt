package hu.tb.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.tb.core.domain.meal.Food
import hu.tb.core.domain.meal.FoodCategory
import hu.tb.core.domain.meal.Measure

@Entity
data class FoodEntity(
    @PrimaryKey(autoGenerate = true) val foodId: Long? = null,
    val foodName: String,
    val category: FoodCategory,
    val quantity: Int,
    val measure: Measure
)

fun FoodEntity.toDomain(): Food =
    Food(
        name = foodName,
        category = category,
        quantity = quantity,
        measure = measure
    )

fun Food.toEntity(): FoodEntity =
    FoodEntity(
        foodName = name,
        category = category,
        quantity = quantity,
        measure = measure
    )



