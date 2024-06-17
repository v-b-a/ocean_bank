package com.ocean.bank.error.handling;

import com.ocean.bank.error.handling.testEnvirament.dto.DemoDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class ErrorHandlingServiceTest extends BaseTest {
    @Test
    public void testSqlException() {
        webTestClient.get()
                .uri("/testSqlException")
                .exchange()
                .expectStatus().isEqualTo(503)
                .expectBody()
                .jsonPath("$.message").value(Matchers.startsWith("ERROR: relation \"demo_entity\" does not exist"))
                .jsonPath("$.time").value(Matchers.notNullValue());

    }

    @Test
    public void testMissingRequestValueExceptionMissingQueryParam() {
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/testMissingRequestValueException")
                        // query param code is missing
                        .build())
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody()
                .jsonPath("$.message").value(Matchers.startsWith("Required request parameter 'code' for method parameter type String is not present"))
                .jsonPath("$.time").value(Matchers.notNullValue());
    }

    @Test
    @Disabled("не шмог подставить боди")
    public void testMissingRequestValueExceptionMissingBodyValidationError() {
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/testMissingRequestValueException")
                        .queryParam("code", "testCode")
                        .build())
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(new DemoDto("testName")).accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(400)
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody()
//                .jsonPath("$.message").value(Matchers.startsWith("Required request parameter 'code' for method parameter type String is not present"))
                .jsonPath("$.time").value(Matchers.notNullValue());
    }

    @Test
    public void notFoundException() {
        webTestClient.get()
                .uri("/testNotFoundException")
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody()
                .jsonPath("$.message").value(Matchers.startsWith("not found exception"))
                .jsonPath("$.time").value(Matchers.notNullValue());
    }
}
