package nl.sogyo.modelr

class Simulation(private val firstUnitOperation: UnitOperation) : ISimulation {

    override fun runSimulation(): OperationOutput {
        return firstUnitOperation.generateOutput()
    }

    override fun getFirstUnitOperation(): UnitOperation {
        return firstUnitOperation
    }
}