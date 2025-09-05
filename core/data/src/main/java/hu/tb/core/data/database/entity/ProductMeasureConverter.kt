package hu.tb.core.data.database.entity

import androidx.room.TypeConverter
import hu.tb.core.domain.product.Measure

class ProductMeasureConverter {

    @TypeConverter
    fun unitToInt(unit: Measure): Int = unit.ordinal


    @TypeConverter
    fun intToUnit(index: Int): Measure =
        when (index) {
            index -> Measure.entries[index]
            else -> throw Exception()
        }
}