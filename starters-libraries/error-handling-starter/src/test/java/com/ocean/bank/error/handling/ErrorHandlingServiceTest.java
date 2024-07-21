package com.ocean.bank.error.handling;

import com.ocean.bank.error.handling.testEnvironment.dto.DemoDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ErrorHandlingServiceTest extends BaseTest {
    @Test
    void testSqlException() throws Exception {
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

    @Test
    void testMissingRequestValueExceptionMissingQueryParam() throws Exception {
        mockMvc.perform(post("/testMissingRequestValueException"))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message")
                        .value(Matchers.startsWith("Required request parameter 'code' for method parameter type String is not present")))
                .andExpect(jsonPath("$.time")
                        .value(Matchers.notNullValue()));
    }

    @Test
    void testMissingRequestValueExceptionMissingBodyValidationError() throws Exception {
        mockMvc.perform(post("/testMissingRequestValueException")
                        .queryParam("code", "testCode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new DemoDto("", 26)))
                ).andExpect(status().is(400))
                .andExpect(jsonPath("$.message").value(Matchers.startsWith("Request does not match contract")))
                .andExpect(jsonPath("$.time").value(Matchers.notNullValue()));
    }

    @Test
    void notFoundException() throws Exception {
        mockMvc.perform(get("/testNotFoundException"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value(Matchers.startsWith("not found exception")));
    }

    @Test
    void loginException() throws Exception {
        mockMvc.perform(get("/testLoginException"))
                .andExpect(status().is(401))
                .andExpect(jsonPath("$.message").value(Matchers.startsWith("login exception")));
    }
}
