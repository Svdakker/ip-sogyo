package nl.sogyo.modelr

interface ISimulationFactory {

    fun createNewSimulation(operations: List<String>, settings: String): ISimulation
}