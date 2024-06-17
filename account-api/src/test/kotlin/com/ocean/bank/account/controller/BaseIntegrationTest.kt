package com.ocean.bank.account.controller

import com.ocean.bank.account.BaseTest
import com.ocean.bank.account.configuration.TestContainerPostgresConfig
import com.ocean.bank.account.repository.entity.Client
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
@ContextConfiguration(classes = [TestContainerPostgresConfig::class])
@ExtendWith(TestContainerPostgresConfig::class)
@Sql("/scripts/setUpDatabase.sql", "/scripts/insertMockData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql("/scripts/cleanDatabase.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class BaseIntegrationTest : BaseTest() {
    @Autowired
    lateinit var wac: WebApplicationContext

    lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setUp(context: ApplicationContext) {
        val mockServerSpec = MockMvcWebTestClient.bindToApplicationContext(wac)
        webTestClient =
            mockServerSpec.configureClient().baseUrl("http://localhost:" + SpringBootTest.WebEnvironment.RANDOM_PORT)
                .build()
    }

    fun createClient(clientId: Long = 1, clientCode: String = CLIENT_CODE) =
        Client(
            clientId = clientId,
            code = clientCode,
            statusId = 1
        )
}

