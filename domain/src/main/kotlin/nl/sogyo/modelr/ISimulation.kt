package nl.sogyo.modelr

interface ISimulation {

    fun runSimulation(): Number

    fun getFirstUnitOperation(): IUnitOperation
}