package com.ocean.bank.account.controller

import com.ocean.bank.account.repository.JDBCTemplateClientDao
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired

class ClientControllerTest : BaseIntegrationTest() {

    @Autowired
    private lateinit var clientRepository: JDBCTemplateClientDao

    @Test
    fun addClientTest() {
        webTestClient.post()
            .uri { it.path(CLIENT_PATH).build() }
            .header("client-code", CLIENT_CODE_2)
            .exchange()
            .expectStatus().isOk

        // FIXME: а тут почему-то могу получить клиента напрямую из БД, в отличии от счетов
        val client = clientRepository.findClientByCode(CLIENT_CODE_2)
        assertAll(
            { assertThat(client).isNotNull },
            { assertThat(client?.code).isEqualTo("AAAAB") },
            { assertThat(client?.statusId).isEqualTo(1) }
        )
    }

    @Test
    fun updateClientStatusTest() {
        clientRepository.saveClient(createClient())

        webTestClient.put()
            .uri { it.path(CLIENT_PATH).queryParam("status", "ACTIVE").build() }
            .header("client-code", CLIENT_CODE)
            .exchange()
            .expectStatus().isOk

        val client = clientRepository.findClientByCode(CLIENT_CODE)
        assertAll(
            { assertThat(client).isNotNull },
            { assertThat(client?.code).isEqualTo(CLIENT_CODE) },
            { assertThat(client?.statusId).isEqualTo(2) }
        )
    }

    companion object {
        const val CLIENT_PATH = "internal/client"
    }
}