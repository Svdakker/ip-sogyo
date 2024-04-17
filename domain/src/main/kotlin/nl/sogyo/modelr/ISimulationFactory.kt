package nl.sogyo.modelr

interface ISimulationFactory {

    fun createNewSimulation(operations: String, settings: String): ISimulation
}