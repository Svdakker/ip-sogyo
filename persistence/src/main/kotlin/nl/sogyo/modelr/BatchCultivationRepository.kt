package nl.sogyo.modelr

import nl.sogyo.modelr.entities.BatchCultivation
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BatchCultivationRepository : CrudRepository<BatchCultivation, Long> {
}