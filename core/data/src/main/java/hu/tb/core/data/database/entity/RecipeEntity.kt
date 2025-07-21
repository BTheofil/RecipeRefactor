package hu.tb.core.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import hu.tb.core.domain.recipe.Recipe

@Entity
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Long?,
    val name: String,
)

fun Recipe.toEntity(): RecipeEntity =
    RecipeEntity(
        recipeId = id,
        name = name
    )

data class RecipeCompleteEntity(
    @Embedded
    val recipeEntity: RecipeEntity,

    @Relation(
        entity = ProductEntity::class,
        parentColumn = "recipeId",
        entityColumn = "recipeIdConnection"
    )
    val productEntity: List<ProductEntity>,

    @Relation(
        entity = StepEntity::class,
        parentColumn = "recipeId",
        entityColumn = "recipeIdConnection"
    )
    val howToMakeSteps: List<StepEntity>,
)
