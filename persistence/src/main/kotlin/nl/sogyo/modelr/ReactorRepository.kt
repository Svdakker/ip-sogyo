package nl.sogyo.modelr

import nl.sogyo.modelr.entities.Reactor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReactorRepository : CrudRepository<Reactor, Long> {
}