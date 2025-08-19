package hu.tb.core.domain.product

enum class Measure() {
    PIECE,
    GRAM,
    DAG,
    KG,
    DL,
    LITER;

    val category: Category
        get() = when (this) {
            PIECE -> Category.PIECE
            GRAM, DAG, KG -> Category.WEIGHT
            DL, LITER -> Category.VOLUME
        }

    val factor: Double
        get() = when (this) {
            PIECE, GRAM, DL -> 1.0
            DAG, LITER -> 10.0
            KG -> 1000.0
        }

    val toDisplay: String
        get() = when (this) {
            GRAM -> "gram"
            DAG -> "dag"
            KG -> "kg"
            PIECE -> "piece"
            DL -> "dl"
            LITER -> "l"
        }

    enum class Category {
        PIECE,
        WEIGHT,
        VOLUME
    }
}