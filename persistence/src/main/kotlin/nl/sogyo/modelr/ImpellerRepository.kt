package nl.sogyo.modelr

import nl.sogyo.modelr.entities.Impeller
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(propagation = Propagation.REQUIRED)
interface ImpellerRepository : CrudRepository<Impeller, Long> {

    fun findImpellerByType(string: String): Impeller?
}