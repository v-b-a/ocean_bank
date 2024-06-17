package com.ocean.bank.account.configuration

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.boot.test.context.TestConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container


@TestConfiguration
class TestContainerPostgresConfig : BeforeAllCallback, AfterAllCallback {

    companion object {

        @JvmStatic
        @Container
        var postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:latest")
            .withDatabaseName("accounts-ob")
            .withUsername("postgres")
            .withPassword("password")

        /*
        @JvmStatic
        @DynamicPropertySource
        fun registerPgProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { "jdbc:postgresql://localhost:${postgreSQLContainer.firstMappedPort}/accounts-ob" }
            registry.add("spring.datasource.username") { postgreSQLContainer.username }
            registry.add("spring.datasource.password") { postgreSQLContainer.password }
            registry.add("spring.datasource.driver-class-name") { postgreSQLContainer.driverClassName }
        }
         */
    }

    override fun beforeAll(p0: ExtensionContext) {
        postgreSQLContainer.start()
        System.setProperty(
            "spring.datasource.url",
            "jdbc:postgresql://localhost:${postgreSQLContainer.firstMappedPort}/accounts-ob"
        )
        System.setProperty("spring.datasource.username", postgreSQLContainer.username)
        System.setProperty("spring.datasource.password", postgreSQLContainer.password)
    }


    override fun afterAll(p0: ExtensionContext?) {
        postgreSQLContainer.close()
        postgreSQLContainer.stop()
    }
}
