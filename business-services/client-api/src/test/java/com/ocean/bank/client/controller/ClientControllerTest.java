package com.ocean.bank.client.controller;

import com.ocean.bank.client.controller.dto.ConfirmRq;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ClientControllerTest extends BaseControllerTest {

    @Test
    void createClientTest() {
        var response = webTestClient.post()
                .bodyValue(getTestClientInfo())
                .exchange();

        response.expectStatus().isOk()
                .expectBody()
                .jsonPath("$.firstName").isEqualTo(TEST_CLIENT_FIRST_NAME)
                .jsonPath("$.lastName").isEqualTo("Doe")
                .jsonPath("$.clientCode").isNotEmpty()
                .jsonPath("$.status").isEqualTo("UNVERIFIED");

        var clients = clientRepository.findAll();

        assertAll(
                () -> assertThat(clients).isNotEmpty(),
                () -> assertThat(clients.stream().anyMatch(client -> client.getFirstName().equals(TEST_CLIENT_FIRST_NAME))).isTrue()
        );
    }

    @Test
    void confirmClientTest() {
        clientRepository.save(getTestClient());
        ConfirmRq confirmCode = new ConfirmRq("1111");

        webTestClient.post()
                .uri(uriInfo -> uriInfo.path("/confirm").build())
                .header("client-code", TEST_CLIENT_CODE)
                .bodyValue(confirmCode)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("ACTIVE");

        assertThat(clientRepository.findClientByClientCode(TEST_CLIENT_CODE).getStatus()).isEqualTo("ACTIVE");
    }

    @Test
    void getClientsTest() {
        clientRepository.save(getTestClient());
        webTestClient.get()
                .uri(uriInfo -> uriInfo.path("/internal").build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].firstName").isEqualTo(TEST_CLIENT_FIRST_NAME)
                .jsonPath("$[0].lastName").isEqualTo("Doe")
                .jsonPath("$[0].clientCode").isNotEmpty()
                .jsonPath("$[0].status").isEqualTo("UNVERIFIED");
    }

    @Test
    void getClientAdditionalInfoTest() {
        clientRepository.save(getTestClient());
        webTestClient.get()
                .uri(uriInfo -> uriInfo.path("/additional-info").build())
                .header("client-code", TEST_CLIENT_CODE)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.clientInfo.firstName").isEqualTo(TEST_CLIENT_FIRST_NAME)
                .jsonPath("$.clientInfo.lastName").isEqualTo("Doe")
                .jsonPath("$.clientAccounts").isEmpty();
    }
}
