package nl.sogyo.modelr

import nl.sogyo.modelr.entities.Centrifugation
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(propagation = Propagation.REQUIRED)
interface CentrifugationRepository : CrudRepository<Centrifugation, Long> {

    @Modifying
    @Query("update Centrifugation c set c.result = :result where c.id = :id")
    fun setResultForCentrifugation(@Param("id") id: Long, @Param("result") result: String)
}