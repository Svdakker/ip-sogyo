package nl.sogyo.modelr

import nl.sogyo.modelr.entities.CostFactor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CostFactorRepository : CrudRepository<CostFactor, Long> {

    fun findCostFactorById(id: Long): CostFactor
}