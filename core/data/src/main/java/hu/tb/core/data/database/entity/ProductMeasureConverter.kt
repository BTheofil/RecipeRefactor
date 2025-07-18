package hu.tb.core.data.database.entity

import androidx.room.TypeConverter
import hu.tb.core.domain.product.Measure

class ProductMeasureConverter {

    @TypeConverter
    fun unitToInt(unit: Measure): Int = unit.ordinal


    @TypeConverter
    fun intToUnit(index: Int): Measure =
        when(index){
            0 -> Measure.PIECE
            1 -> Measure.GRAM
            2 -> Measure.DAG
            3 -> Measure.KG
            else -> throw Exception()
        }
}