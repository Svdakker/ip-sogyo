package nl.sogyo.modelr

import nl.sogyo.modelr.entities.Microorganism
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MicroorganismRepository : CrudRepository<Microorganism, Long> {

    fun findMicroorganismsByName(name: String): Microorganism?
}