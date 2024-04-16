package nl.sogyo.modelr

class SimulationFactory : ISimulationFactory {

    override fun createNewSimulation(): Simulation {
        return Simulation(BatchFermentation(FermentationInput(20.00, 0.12, 0.27, 0.00703, 0.4)))
    }
}