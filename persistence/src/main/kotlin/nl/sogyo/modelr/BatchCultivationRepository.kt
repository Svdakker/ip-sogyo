package nl.sogyo.modelr

import nl.sogyo.modelr.entities.BatchCultivation
import nl.sogyo.modelr.entities.Request
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(propagation = Propagation.REQUIRED)
interface BatchCultivationRepository : CrudRepository<BatchCultivation, Long> {

    fun findByRequest(request: Request): BatchCultivation?

    @Modifying
    @Query("update BatchCultivation b set b.result = :result where b.id = :id")
    fun setResultForBatchCultivation(@Param("id") id: Long, @Param("result") result: String)
}