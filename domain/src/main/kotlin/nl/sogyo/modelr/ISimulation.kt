package nl.sogyo.modelr

import nl.sogyo.modelr.data.OperationOutput

interface ISimulation {

    fun runSimulation(): OperationOutput

    fun getFirstUnitOperation(): UnitOperation
}