package hu.tb.core.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import hu.tb.core.domain.step.Step

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
data class StepEntity(
    @PrimaryKey(autoGenerate = true)
    val stepId: Long? = null ,
    val description: String,
    val recipeIdConnection: Long
)

fun StepEntity.toDomain() =
    Step(
        id = stepId!!,
        description = description
    )