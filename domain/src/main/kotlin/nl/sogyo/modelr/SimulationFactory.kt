package nl.sogyo.modelr

class SimulationFactory : ISimulationFactory {

    override fun createNewSimulation(): Simulation {
        return Simulation()
    }
}