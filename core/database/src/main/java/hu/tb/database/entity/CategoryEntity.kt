package hu.tb.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val categoryId: Long? = null,
    val categoryName: String
)