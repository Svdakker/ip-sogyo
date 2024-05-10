package nl.sogyo.modelr

import nl.sogyo.modelr.data.OperationOutput
import nl.sogyo.modelr.data.SimulationOutput

class Simulation(private val firstUnitOperation: UnitOperation) : ISimulation {

    override fun runSimulation(): SimulationOutput {
        return SimulationOutput(runUnitOperations())
    }

    private fun runUnitOperations(): List<OperationOutput> {
        val output = listOf(this.firstUnitOperation.generateOutput())
        return runNextOperations(output, this.firstUnitOperation)
    }

    private fun runNextOperations(accumulator: List<OperationOutput>, previousOperation: UnitOperation?): List<OperationOutput> {
        return if (previousOperation!!.getNextOperation() != null) {
            val currentOperation = previousOperation.getNextOperation()
            val output = currentOperation!!.generateOutput(accumulator.last(), previousOperation)
            runNextOperations(accumulator + output, currentOperation)
        } else {
            accumulator
        }
    }

    override fun getFirstUnitOperation(): UnitOperation {
        return firstUnitOperation
    }
}