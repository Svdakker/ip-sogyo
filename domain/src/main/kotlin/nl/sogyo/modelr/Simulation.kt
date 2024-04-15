package nl.sogyo.modelr

class Simulation(private val firstUnitOperation: IUnitOperation = BatchFermentation()) : ISimulation {

    override fun runSimulation(): Number {
        return firstUnitOperation.calculateDuration()
    }

    override fun getFirstUnitOperation(): IUnitOperation {
        return firstUnitOperation
    }
}