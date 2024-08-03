package com.ocean.bank.test.lib

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
open class BasePostgresTest: AfterAllCallback, BaseTest() {

    companion object {

        private val postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:latest")
            .withDatabaseName("accounts-ob")
            .withUsername("postgres")
            .withPassword("password")
            .withReuse(true)


        @JvmStatic
        @DynamicPropertySource
        fun registerPgProperties(registry: DynamicPropertyRegistry) {
            postgreSQLContainer.start()
            registry.add("spring.datasource.url") { "jdbc:postgresql://localhost:${postgreSQLContainer.firstMappedPort}/accounts-ob" }
            registry.add("spring.datasource.username") { postgreSQLContainer.username }
            registry.add("spring.datasource.password") { postgreSQLContainer.password }
            registry.add("spring.datasource.driver-class-name") { postgreSQLContainer.driverClassName }
        }
    }

    override fun afterAll(p0: ExtensionContext?) {
        postgreSQLContainer.close()
        postgreSQLContainer.stop()
    }
}
