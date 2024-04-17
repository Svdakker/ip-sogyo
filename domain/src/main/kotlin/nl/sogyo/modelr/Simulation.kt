package nl.sogyo.modelr

import nl.sogyo.modelr.data.OperationOutput

class Simulation(private val firstUnitOperation: UnitOperation) : ISimulation {

    override fun runSimulation(): OperationOutput {
        return firstUnitOperation.generateOutput(1.0)
    }

    override fun getFirstUnitOperation(): UnitOperation {
        return firstUnitOperation
    }
}