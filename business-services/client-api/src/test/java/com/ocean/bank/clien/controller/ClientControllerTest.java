//package com.ocean.bank.clien.controller;
//
//import com.ocean.bank.client.controller.dto.ClientInfo;
//import com.ocean.bank.client.repository.entity.Address;
//import com.ocean.bank.client.repository.entity.IdentificationDocument;
//import com.ocean.bank.client.repository.entity.SensitiveData;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertAll;
//
//class ClientControllerTest extends BaseControllerTest {
//
//    @Test
//    void createClient() {
//        var clientInfo = ClientInfo.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .identificationDocument(
//                        IdentificationDocument.builder().documentName("passport").number("123456").code("123456").build())
//                .address(Address.builder().registrationAddress("registrationAddress").build())
//                .sensitiveData(SensitiveData.builder().passphrase("passphrase").password("password").build())
//                .phoneNumber("123456")
//                .build();
//
//
//        var response = webTestClient.post()
//                .uri("")
//                .bodyValue(clientInfo)
//                .exchange()
//                .expectStatus().isOk();
//
//        assertAll(
//                () -> assertThat(response.expectBody().jsonPath("firstName").isEqualTo("John")),
//                () -> assertThat(response.expectBody().jsonPath("lastName").isEqualTo("Doe")),
//                () -> assertThat(response.expectBody().jsonPath("clientCode").isNotEmpty()),
//                () -> assertThat(response.expectBody().jsonPath("status").isNotEmpty())
//        );
//    }
//}
