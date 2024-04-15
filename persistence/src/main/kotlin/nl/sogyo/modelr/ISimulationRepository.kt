package nl.sogyo.modelr

import nl.sogyo.modelr.entities.SimulationResult
import org.springframework.data.repository.CrudRepository

interface ISimulationRepository : CrudRepository<SimulationResult, Long> {

}