package nl.sogyo.modelr

import nl.sogyo.modelr.entities.CostFactor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Repository
@Transactional(propagation = Propagation.REQUIRED)
interface CostFactorRepository : CrudRepository<CostFactor, Long> {

    fun findAllByDate(date: LocalDate): List<CostFactor>

    fun findFirstByOrderByDateDesc(): CostFactor?

    fun findCostFactorById(id: Long): CostFactor
}