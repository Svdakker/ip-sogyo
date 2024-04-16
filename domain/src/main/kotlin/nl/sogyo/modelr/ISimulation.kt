package nl.sogyo.modelr

interface ISimulation {

    fun runSimulation(): OperationOutput

    fun getFirstUnitOperation(): UnitOperation
}