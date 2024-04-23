package nl.sogyo.modelr

import nl.sogyo.modelr.entities.Impeller
import nl.sogyo.modelr.entities.Microorganism
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

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