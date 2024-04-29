package nl.sogyo.modelr

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
internal class TestContextDataSourceConfiguration {
    @Bean
    fun dataSource() = CleanDatabaseDataSourceExtension.dataSource
}
