package com.ocean.bank.error.handling;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ErrorHandlingServiceTest extends BaseTest {
    @Test
    public void testSqlException() throws Exception {
        mockMvc.perform(get("/testSqlException")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(503))
                .andExpect(jsonPath("$.message")
                        .value(Matchers.startsWith("ERROR: relation \"demo_entity\" does not exist")))
                .andExpect(jsonPath("$.time")
                        .value(Matchers.notNullValue()));
    }

//    @Test
//    public void testMissingRequestValueExceptionMissingQueryParam() {
//        webTestClient.post()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/testMissingRequestValueException")
//                        // query param code is missing
//                        .build())
//                .exchange()
//                .expectStatus().isEqualTo(400)
//                .expectBody()
//                .jsonPath("$.message").value(Matchers.startsWith("Required request parameter 'code' for method parameter type String is not present"))
//                .jsonPath("$.time").value(Matchers.notNullValue());
//    }
//
//    @Test
//    @Disabled("не шмог подставить боди")
//    public void testMissingRequestValueExceptionMissingBodyValidationError() {
//        webTestClient.post()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/testMissingRequestValueException")
//                        .queryParam("code", "testCode")
//                        .build())
////                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .bodyValue(new DemoDto("testName")).accept(APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isEqualTo(400)
////                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
//                .expectBody()
////                .jsonPath("$.message").value(Matchers.startsWith("Required request parameter 'code' for method parameter type String is not present"))
//                .jsonPath("$.time").value(Matchers.notNullValue());
//    }
//
//    @Test
//    public void notFoundException() {
//        webTestClient.get()
//                .uri("/testNotFoundException")
//                .exchange()
//                .expectStatus().isEqualTo(404)
//                .expectBody()
//                .jsonPath("$.message").value(Matchers.startsWith("not found exception"))
//                .jsonPath("$.time").value(Matchers.notNullValue());
//    }
}
