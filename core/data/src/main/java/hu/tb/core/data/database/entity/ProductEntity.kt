package hu.tb.core.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import hu.tb.core.domain.product.Product
import hu.tb.core.domain.product.Measure

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["recipeId"],
            childColumns = ["recipeIdConnection"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val productId: Long? = null,
    val name: String,
    val quantity: Double,
    val measure: Measure,
    val recipeIdConnection: Long? = null,
    val isChecked: Boolean? = null //null means is for the shopping
)

fun ProductEntity.toDomain(): Product =
    Product(
        id = productId,
        name = name,
        quantity = quantity,
        measure = measure
    )

fun Product.toEntity(recipeConnectionId: Long? = null): ProductEntity =
    ProductEntity(
        name = name,
        quantity = quantity,
        measure = measure,
        recipeIdConnection = recipeConnectionId,
    )



