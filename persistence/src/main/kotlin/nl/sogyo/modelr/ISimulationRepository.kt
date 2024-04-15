package nl.sogyo.modelr

import nl.sogyo.modelr.entities.SimulationResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ISimulationRepository : JpaRepository<SimulationResult, Long> {

}