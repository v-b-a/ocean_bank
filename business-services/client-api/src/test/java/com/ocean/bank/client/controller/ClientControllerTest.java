package com.ocean.bank.client.controller;

import com.ocean.bank.client.controller.dto.ClientInfo;
import com.ocean.bank.client.repository.entity.Address;
import com.ocean.bank.client.repository.entity.IdentificationDocument;
import com.ocean.bank.client.repository.entity.SensitiveData;
import org.junit.jupiter.api.Test;

class ClientControllerTest extends BaseControllerTest {

    @Test
    void createClientTest() {
        var clientInfo = ClientInfo.builder()
                .firstName("John")
                .lastName("Doe")
                .identificationDocument(
                        IdentificationDocument.builder().documentName("passport").number("123456").code("123456").build())
                .address(Address.builder().registrationAddress("registrationAddress").build())
                .sensitiveData(SensitiveData.builder().passphrase("passphrase").password("password").build())
                .phoneNumber("123456")
                .build();


        var response = webTestClient.post()
                .uri("")
                .bodyValue(clientInfo)
                .exchange();

        response.expectStatus().isOk()
                .expectBody()
                .jsonPath("$.firstName").isEqualTo("John")
                .jsonPath("$.lastName").isEqualTo("Doe")
                .jsonPath("$.clientCode").isNotEmpty()
                .jsonPath("$.status").isNotEmpty();
    }
}
