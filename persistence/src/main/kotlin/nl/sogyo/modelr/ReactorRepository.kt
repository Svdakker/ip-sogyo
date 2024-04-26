package nl.sogyo.modelr

import nl.sogyo.modelr.entities.Reactor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(propagation = Propagation.REQUIRED)
interface ReactorRepository : CrudRepository<Reactor, Long> {

    fun findReactorByName(name: String): Reactor?

    @Query("select r.name from Reactor r")
    fun findAllTypes(): List<String?>
}