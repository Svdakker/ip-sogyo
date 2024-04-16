package nl.sogyo.modelr

interface ISimulationFactory {

    enum class UnitOperations{
        BATCH_FERMENTATION
    }

    fun createNewSimulation(operations: String, settings: String): ISimulation
}