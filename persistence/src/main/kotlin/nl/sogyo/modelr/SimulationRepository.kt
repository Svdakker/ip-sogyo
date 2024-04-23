package nl.sogyo.modelr

import nl.sogyo.modelr.entities.Simulation
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SimulationRepository : CrudRepository<Simulation, Long> {

}