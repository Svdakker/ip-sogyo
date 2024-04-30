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

    private fun runNextOperations(output: List<OperationOutput>, accumulator: UnitOperation?): List<OperationOutput> {
        return if (accumulator!!.getNextOperation() != null) {
            runNextOperations(output + accumulator.getNextOperation()!!.generateOutput(), accumulator.getNextOperation())
        } else {
            output
        }
    }

    override fun getFirstUnitOperation(): UnitOperation {
        return firstUnitOperation
    }
}