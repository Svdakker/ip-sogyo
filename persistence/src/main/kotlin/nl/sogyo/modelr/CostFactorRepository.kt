package nl.sogyo.modelr

import nl.sogyo.modelr.entities.CostFactor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface CostFactorRepository : CrudRepository<CostFactor, Long> {

    fun findCostFactorById(id: Long): CostFactor
}