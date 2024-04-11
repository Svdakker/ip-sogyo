package nl.sogyo.modelr

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DummyTest {

    private val dummy = Dummy()

    @Test
    fun dummyTest() {
        assertTrue(dummy.alwaysTrue())
    }
}