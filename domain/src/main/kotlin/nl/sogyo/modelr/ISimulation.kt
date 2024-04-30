package nl.sogyo.modelr

import nl.sogyo.modelr.data.SimulationOutput

interface ISimulation {

    fun runSimulation(): SimulationOutput

    fun getFirstUnitOperation(): UnitOperation
}