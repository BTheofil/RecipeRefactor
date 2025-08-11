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
    val quantity: Int,
    val measure: Measure,
    val isDraft: Boolean, //for the shopping items, also the isChecked
    val recipeIdConnection: Long? = null,
    val isChecked: Boolean = false
)

fun ProductEntity.toDomain(): Product =
    Product(
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
        isDraft = false,
    )



