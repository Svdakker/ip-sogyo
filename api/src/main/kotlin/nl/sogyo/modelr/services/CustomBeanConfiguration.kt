package nl.sogyo.modelr.services

import nl.sogyo.modelr.SimulationFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomBeanConfiguration {

    @Bean
    fun simulationFactoryBean(): SimulationFactory {
        return SimulationFactory()
    }
}