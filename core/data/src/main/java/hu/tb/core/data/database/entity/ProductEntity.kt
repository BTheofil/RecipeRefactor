package hu.tb.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.tb.core.domain.product.Product
import hu.tb.core.domain.product.Measure

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val foodId: Long? = null,
    val foodName: String,
    val quantity: Int,
    val measure: Measure
)

fun ProductEntity.toDomain(): Product =
    Product(
        name = foodName,
        quantity = quantity,
        measure = measure
    )

fun Product.toEntity(): ProductEntity =
    ProductEntity(
        foodName = name,
        quantity = quantity,
        measure = measure
    )



