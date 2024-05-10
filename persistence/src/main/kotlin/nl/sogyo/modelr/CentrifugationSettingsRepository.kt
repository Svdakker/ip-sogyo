package nl.sogyo.modelr

import nl.sogyo.modelr.entities.CentrifugationSettings
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(propagation = Propagation.REQUIRED)
interface CentrifugationSettingsRepository : CrudRepository<CentrifugationSettings, Long>