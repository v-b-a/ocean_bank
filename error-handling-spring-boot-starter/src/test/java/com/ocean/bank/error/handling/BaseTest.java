package com.ocean.bank.error.handling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocean.bank.error.handling.service.ErrorHandlingService;
import com.ocean.bank.error.handling.testEnvironment.controller.TestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = TestController.class)
@ContextConfiguration(classes = {ErrorHandlingService.class, TestController.class})
public class BaseTest {
    @Autowired
    MockMvc mockMvc;

    public static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
