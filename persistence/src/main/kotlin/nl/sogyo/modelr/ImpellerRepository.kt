package nl.sogyo.modelr

import nl.sogyo.modelr.entities.Impeller
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ImpellerRepository : CrudRepository<Impeller, Long> {

    fun findImpellerByType(string: String): Impeller?
}