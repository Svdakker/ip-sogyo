package nl.sogyo.modelr.integration

import nl.sogyo.modelr.ReactorRepository
import nl.sogyo.modelr.RealDatabaseTest
import nl.sogyo.modelr.entities.Reactor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

@RealDatabaseTest
class ReactorRepositoryIntegrationTest {

    @Autowired
    lateinit var reactorRepository: ReactorRepository

    @Test
    fun testFindReactorByNameReturnsMatchingReactor() {
        val creation = LocalDate.now()
        val reactor = Reactor(creation, "example", 70.0, 52.5, 9.29, 3.10, null)
        reactorRepository.save(reactor)

        val result = reactorRepository.findReactorByName("example")

        assertEquals(reactor.id, result?.id)
        assertEquals(1L, result?.id)
        assertEquals(reactor.date, result?.date)
        assertEquals(reactor.name, result?.name)
        assertEquals(reactor.nominalVolume, result?.nominalVolume)
        assertEquals(reactor.workingVolume, result?.workingVolume)
        assertEquals(reactor.height, result?.height)
        assertEquals(reactor.width, result?.width)
    }
}