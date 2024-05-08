package nl.sogyo.modelr

import nl.sogyo.modelr.entities.Centrifuge
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(propagation = Propagation.REQUIRED)
interface CentrifugeRepository : CrudRepository<Centrifuge, Long> {

    fun findCentrifugeByName(name: String): Centrifuge?

    @Query("select c.name from Centrifuge c")
    fun findAllNames(): List<String?>
}