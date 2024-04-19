package nl.sogyo.modelr.data

data class CultivationSettings(
        val accuracy: Double,
        val initialSugarConcentration: Double, //kg m-3
        val initialCellDensity: Double, //kg m-3
        val maxGrowthRate: Double, //h-1
        val maintenance: Double, //kg kgx-1 h-1
        val yield: Double, //-
)
