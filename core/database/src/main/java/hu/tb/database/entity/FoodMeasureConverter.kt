package hu.tb.database.entity

import androidx.room.TypeConverter
import hu.tb.core.domain.meal.Measure

class FoodMeasureConverter {

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