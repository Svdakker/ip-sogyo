package nl.sogyo.modelr.integration

import nl.sogyo.modelr.ImpellerRepository
import nl.sogyo.modelr.RealDatabaseTest
import nl.sogyo.modelr.entities.Impeller
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@RealDatabaseTest
class ImpellerRepositoryIntegrationTest {

    @Autowired
    lateinit var impellerRepository: ImpellerRepository

    @Test
    fun testFindByImpellerTypeIsReturningMatchingImpeller() {
        val impeller = Impeller("rushton turbine", 0.97, 0.72, 5.2, null)
        impellerRepository.save(impeller)

        val result = impellerRepository.findImpellerByType("rushton turbine")

        assertEquals(impeller.impellerDiameter, result?.impellerDiameter)
        assertEquals(impeller.impellerFlowNumber, result?.impellerFlowNumber)
        assertEquals(impeller.impellerPowerNumber, result?.impellerPowerNumber)
        assertEquals(impeller.id, result?.id)
        assertEquals(1L, result?.id)
    }
}