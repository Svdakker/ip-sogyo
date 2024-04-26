package nl.sogyo.modelr

import nl.sogyo.modelr.entities.Simulation
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(propagation = Propagation.REQUIRED)
interface SimulationRepository : CrudRepository<Simulation, Long> {

    @Query("SELECT s FROM Simulation s ORDER BY s.id DESC LIMIT 1")
    fun findLatest(): Simulation?
}